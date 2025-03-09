package com.example.survey.screens.survey.domain

interface SubmitAnswerUseCase {
    suspend operator fun invoke(questionId: Int, answer: String): Result<Unit>
}

class SubmitAnswerUseCaseImpl(private val repository: SurveyRepository) : SubmitAnswerUseCase {
    override suspend fun invoke(questionId: Int, answer: String): Result<Unit> =
        repository.submitAnswer(questionId = questionId, answer = answer)
}
