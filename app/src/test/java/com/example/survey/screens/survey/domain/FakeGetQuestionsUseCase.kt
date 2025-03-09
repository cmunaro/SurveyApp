package com.example.survey.screens.survey.domain

import com.example.survey.screens.survey.domain.model.QuestionDomain

class FakeGetQuestionsUseCase(
    private val shouldFail: Boolean = false
) : GetQuestionsUseCase {
    override suspend fun invoke(): Result<List<QuestionDomain>> {
        return if (shouldFail) Result.failure(exception)
        else Result.success(questions)
    }

    companion object {
        val exception = Exception()
        val questions = listOf(
            QuestionDomain(
                id = 1,
                query = "A"
            ),
            QuestionDomain(
                id = 2,
                query = "B"
            )
        )
    }
}