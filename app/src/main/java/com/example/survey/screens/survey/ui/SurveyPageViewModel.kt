package com.example.survey.screens.survey.ui

import androidx.lifecycle.viewModelScope
import com.example.survey.screens.survey.domain.GetQuestionsUseCase
import com.example.survey.screens.survey.domain.SubmitAnswerUseCase
import com.example.survey.screens.survey.domain.model.toQuestions
import com.example.survey.utils.Async
import com.example.survey.utils.StateViewModel
import com.example.survey.utils.getOrThrow
import com.example.survey.utils.mapValue
import kotlinx.coroutines.launch

class SurveyPageViewModel(
    private val getQuestionsUseCase: GetQuestionsUseCase,
    private val submitAnswerUseCase: SubmitAnswerUseCase
): StateViewModel<SurveyPageState>(
    initialValue = SurveyPageState()
) {
    init {
        loadQuestions()
    }

    private fun loadQuestions() = viewModelScope.launch {
        getQuestionsUseCase()
            .onFailure {
                updateState { copy(asyncQuestions = Async.Failure(it)) }
            }
            .onSuccess { domainQuestions ->
                updateState { copy(asyncQuestions = Async.Success(domainQuestions.toQuestions())) }
            }
    }

    fun onAnswerChange(questionId: Int, newAnswer: String) {
        updateState {
            copy(
                asyncQuestions = asyncQuestions.mapValue { questions ->
                    questions.map { question ->
                        if (question.id == questionId) {
                            question.copy(answer = Answer(newAnswer))
                        } else question
                    }
                }
            )
        }
    }

    fun onAnswerSubmit(questionId: Int) {
        if (state.value.submission == Submission.IN_PROGRESS) return
        updateState { copy(submission = Submission.IN_PROGRESS) }
        viewModelScope.launch {
            val successfullySubmitted = runCatching {
                val question = state.value.asyncQuestions.getOrThrow()
                    .first { it.id == questionId }
                submitAnswerUseCase(questionId = question.id, answer = question.answer.value)
                    .getOrThrow()
            }.isSuccess

            updateState {
                copy(
                    asyncQuestions = asyncQuestions.mapValue { questions ->
                        questions.map { question ->
                            if (question.id == questionId) {
                                question.copy(submitted = successfullySubmitted)
                            } else question
                        }
                    },
                    submission = Submission.IDLE
                )
            }
        }
    }
}