package com.example.survey.screens.survey.domain.model

import com.example.survey.screens.survey.Query
import com.example.survey.screens.survey.Question

fun List<QuestionDomain>.toQuestions(): List<Question> = map { it.toQuestion() }

fun QuestionDomain.toQuestion(): Question = Question(
    id = id,
    query = Query(query)
)