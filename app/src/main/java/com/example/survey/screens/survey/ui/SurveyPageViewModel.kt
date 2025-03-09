package com.example.survey.screens.survey.ui

import androidx.lifecycle.viewModelScope
import com.example.survey.screens.survey.domain.GetQuestionsUseCase
import com.example.survey.screens.survey.domain.model.toQuestions
import com.example.survey.utils.Async
import com.example.survey.utils.StateViewModel
import com.example.survey.utils.mapValue
import kotlinx.coroutines.launch

class SurveyPageViewModel(
    private val getQuestionsUseCase: GetQuestionsUseCase
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

    fun onAnswerChange(questionId: String, newAnswer: String) {
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

    fun onAnswerSubmit() {
    }
}