package com.example.survey.screens.survey.data

import com.example.survey.screens.survey.data.model.QuestionData
import com.example.survey.screens.survey.data.model.toQuestionsDomain
import com.example.survey.screens.survey.domain.SurveyRepository
import com.example.survey.screens.survey.domain.model.QuestionDomain
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SurveyRepositoryImpl(
    private val surveyAPI: SurveyAPI,
    private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO
) : SurveyRepository {
    override suspend fun fetchQuestions(): Result<List<QuestionDomain>> =
        withContext(coroutineDispatcher) {
            runCatching {
                surveyAPI.fetchQuestions()
            }.map(List<QuestionData>::toQuestionsDomain)
        }
}