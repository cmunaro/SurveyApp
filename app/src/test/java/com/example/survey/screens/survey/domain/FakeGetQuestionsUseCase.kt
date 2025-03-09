package com.example.survey.screens.survey.domain

import com.example.survey.screens.survey.domain.model.QuestionDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeGetQuestionsUseCase(
    private val shouldFail: Boolean = false
) : GetQuestionsUseCase {
    override fun invoke(): Flow<List<QuestionDomain>> = flow {
        if (shouldFail) throw exception
        else emit(questions)
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