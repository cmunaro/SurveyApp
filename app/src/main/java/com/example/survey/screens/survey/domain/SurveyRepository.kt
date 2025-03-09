package com.example.survey.screens.survey.domain

import com.example.survey.screens.survey.domain.model.QuestionDomain
import kotlinx.coroutines.flow.Flow

interface SurveyRepository {
    fun fetchQuestions(): Flow<List<QuestionDomain>>
    fun submitAnswer(questionId: Int, answer: String): Flow<Unit>
}