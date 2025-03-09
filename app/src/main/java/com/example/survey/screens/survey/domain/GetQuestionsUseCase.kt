package com.example.survey.screens.survey.domain

import com.example.survey.screens.survey.domain.model.QuestionDomain
import kotlinx.coroutines.flow.Flow

interface GetQuestionsUseCase {
    operator fun invoke(): Flow<List<QuestionDomain>>
}

class GetQuestionsUseCaseImpl(private val repository: SurveyRepository) : GetQuestionsUseCase {
    override fun invoke(): Flow<List<QuestionDomain>> = repository.fetchQuestions()
}
