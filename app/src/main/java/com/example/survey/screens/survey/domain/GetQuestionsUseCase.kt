package com.example.survey.screens.survey.domain

import com.example.survey.screens.survey.domain.model.QuestionDomain

interface GetQuestionsUseCase {
    suspend operator fun invoke(): Result<List<QuestionDomain>>
}

class GetQuestionsUseCaseImpl(private val repository: SurveyRepository) : GetQuestionsUseCase {
    override suspend fun invoke(): Result<List<QuestionDomain>> = repository.fetchQuestions()
}
