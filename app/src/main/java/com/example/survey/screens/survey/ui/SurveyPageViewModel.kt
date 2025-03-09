package com.example.survey.screens.survey.ui

import androidx.lifecycle.viewModelScope
import com.example.survey.screens.survey.domain.GetQuestionsUseCase
import com.example.survey.screens.survey.domain.SubmitAnswerUseCase
import com.example.survey.screens.survey.domain.model.toQuestions
import com.example.survey.utils.Async
import com.example.survey.utils.StateViewModel
import com.example.survey.utils.getOrThrow
import com.example.survey.utils.mapValue
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds

class SurveyPageViewModel(
    private val getQuestionsUseCase: GetQuestionsUseCase,
    private val submitAnswerUseCase: SubmitAnswerUseCase
) : StateViewModel<SurveyPageState>(
    initialValue = SurveyPageState()
) {
    private var submitJobs: MutableMap<Int, Job> = mutableMapOf()

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
        submitJobs[questionId]?.cancel()
        submitJobs[questionId] = viewModelScope.launch {
            updateQuestionSubmissionState(
                questionId = questionId,
                submissionState = Async.Loading(null),
                submissionAlert = SubmissionAlert.NONE
            )

            val successfullySubmitted = runCatching {
                val question = state.value.asyncQuestions.getOrThrow()
                    .first { it.id == questionId }
                submitAnswerUseCase(questionId = question.id, answer = question.answer.value)
                    .getOrThrow()
            }.isSuccess

            updateQuestionSubmissionState(
                questionId = questionId,
                submissionState = Async.Success(successfullySubmitted),
                submissionAlert = if (successfullySubmitted) SubmissionAlert.SUCCESS
                else SubmissionAlert.FAILURE
            )

            delay(3.seconds)
            updateQuestionSubmissionState(
                questionId = questionId,
                submissionAlert = SubmissionAlert.NONE
            )
        }
    }

    private fun updateQuestionSubmissionState(
        questionId: Int,
        submissionState: Async<Boolean>? = null,
        submissionAlert: SubmissionAlert? = null
    ) {
        updateState {
            copy(
                asyncQuestions = asyncQuestions.mapValue { questions ->
                    questions.map { question ->
                        if (question.id == questionId) {
                            question.copy(
                                submitted = submissionState?: question.submitted,
                                submissionAlert = submissionAlert?: question.submissionAlert
                            )
                        } else question
                    }
                }
            )
        }
    }
}