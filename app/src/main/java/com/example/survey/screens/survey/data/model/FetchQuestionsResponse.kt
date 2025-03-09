package com.example.survey.screens.survey.data.model

@JvmInline
value class FetchQuestionsResponse(val value: List<QuestionData>)

data class QuestionData(
    val id: String,
    val question: String,
)
