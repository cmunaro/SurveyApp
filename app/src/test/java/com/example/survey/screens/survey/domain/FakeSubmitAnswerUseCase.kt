package com.example.survey.screens.survey.domain

class FakeSubmitAnswerUseCase(
    private val shouldFail: Boolean = false
): SubmitAnswerUseCase {
    override suspend operator fun invoke(questionId: Int, answer: String): Result<Unit> {
        return if(shouldFail) Result.failure(Exception())
        else Result.success(Unit)
    }
}
