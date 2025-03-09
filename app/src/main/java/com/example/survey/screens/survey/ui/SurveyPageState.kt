package com.example.survey.screens.survey.ui

import androidx.compose.runtime.Immutable
import com.example.survey.utils.Async
import com.example.survey.utils.getOrElse
import com.example.survey.utils.mapValue

@Immutable
data class SurveyPageState(
    val asyncQuestions: Async<List<Question>> = Async.Uninitialized
) {
    val numberOfSubmittedQuestions: Int = asyncQuestions.mapValue { questions ->
        questions.count { it.submitted.getOrElse(default = false) }
    }.getOrElse(0)
}

@Immutable
data class Question(
    val id: Int,
    val query: Query,
    val answer: Answer = Answer.Empty,
    val submitted: Async<Boolean> = Async.Success(false),
    val submissionAlert: SubmissionAlert = SubmissionAlert.NONE
)

@JvmInline
value class Answer(val value: String) {
    companion object {
        val Empty = Answer("")
    }
}

@JvmInline
value class Query(val value: String)

enum class SubmissionAlert {
    NONE, SUCCESS, FAILURE
}
