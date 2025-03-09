package com.example.survey.screens.survey.domain

import kotlinx.coroutines.flow.Flow

interface SubmitAnswerUseCase {
    suspend operator fun invoke(questionId: Int, answer: String): Flow<Unit>
}

class SubmitAnswerUseCaseImpl(private val repository: SurveyRepository) : SubmitAnswerUseCase {
    override suspend fun invoke(questionId: Int, answer: String): Flow<Unit> =
        repository.submitAnswer(questionId = questionId, answer = answer)
}
