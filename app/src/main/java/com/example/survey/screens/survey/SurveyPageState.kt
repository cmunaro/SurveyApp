package com.example.survey.screens.survey

import androidx.compose.runtime.Immutable
import com.example.survey.utils.Async
import com.example.survey.utils.getOrElse
import com.example.survey.utils.mapValue

@Immutable
data class SurveyPageState(
    val asyncQuestions: Async<List<Question>> = Async.Uninitialized,
    val submission: Submission = Submission.IDLE
) {
    val numberOfSubmittedQuestions: Int = asyncQuestions.mapValue { questions ->
        questions.count(Question::submitted)
    }.getOrElse(0)
}

@Immutable
data class Question(
    val id: String,
    val query: Query,
    val answer: Answer = Answer.Empty,
    val submitted: Boolean = false
)

@JvmInline
value class Answer(val value: String) {
    companion object {
        val Empty = Answer("")
    }
}

@JvmInline
value class Query(val value: String)

enum class Submission {
    IDLE, IN_PROGRESS
}
