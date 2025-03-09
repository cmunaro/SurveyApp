package com.example.survey.screens.survey.domain

import com.example.survey.screens.survey.domain.model.QuestionDomain

interface SurveyRepository {
    suspend fun fetchQuestions(): Result<List<QuestionDomain>>
    suspend fun submitAnswer(questionId: Int, answer: String): Result<Unit>
}