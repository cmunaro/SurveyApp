package com.example.survey.screens.survey

import com.example.survey.utils.Async
import com.example.survey.utils.StateViewModel
import com.example.survey.utils.mapValue

class SurveyPageViewModel: StateViewModel<SurveyPageState>(
    initialValue = SurveyPageState()
) {
    init {
        updateState {
            SurveyPageState(
                asyncQuestions = Async.Success(
                    listOf(
                        Question(
                            id = "1",
                            query = Query("What is your name?")
                        ),
                        Question(
                            id = "2",
                            query = Query("What is your feet size?")
                        )
                    )
                )
            )
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