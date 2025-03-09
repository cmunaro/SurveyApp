package com.example.survey.screens.survey.domain

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeSubmitAnswerUseCase(
    private val shouldFail: Boolean = false
): SubmitAnswerUseCase {
    override suspend operator fun invoke(questionId: Int, answer: String): Flow<Unit> = flow {
        if(shouldFail) throw Exception()
        else emit(Unit)
    }
}
