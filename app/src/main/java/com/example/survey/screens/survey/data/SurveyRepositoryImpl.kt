package com.example.survey.screens.survey.data

import com.example.survey.screens.survey.data.model.AnswerData
import com.example.survey.screens.survey.data.model.QuestionData
import com.example.survey.screens.survey.data.model.toQuestionsDomain
import com.example.survey.screens.survey.domain.SurveyRepository
import com.example.survey.screens.survey.domain.model.QuestionDomain
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class SurveyRepositoryImpl(
    private val surveyAPI: SurveyAPI,
    private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO
) : SurveyRepository {
    override fun fetchQuestions(): Flow<List<QuestionDomain>> = flow {
        emit(surveyAPI.fetchQuestions())
    }.map(List<QuestionData>::toQuestionsDomain)
        .flowOn(coroutineDispatcher)

    override fun submitAnswer(questionId: Int, answer: String): Flow<Unit> = flow {
        val response = surveyAPI.submitAnswer(
            answer = AnswerData(id = questionId, answer = answer)
        )
        emit(response)
    }.flowOn(coroutineDispatcher)
}