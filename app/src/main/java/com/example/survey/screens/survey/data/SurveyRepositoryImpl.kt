package com.example.survey.screens.survey.data

import com.example.survey.screens.survey.data.model.AnswerData
import com.example.survey.screens.survey.data.model.QuestionData
import com.example.survey.screens.survey.data.model.toQuestionsDomain
import com.example.survey.screens.survey.domain.SurveyRepository
import com.example.survey.screens.survey.domain.model.QuestionDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class SurveyRepositoryImpl(
    private val surveyAPI: SurveyAPI
) : SurveyRepository {
    override fun fetchQuestions(): Flow<List<QuestionDomain>> = flow {
        emit(surveyAPI.fetchQuestions())
    }.map(List<QuestionData>::toQuestionsDomain)

    override fun submitAnswer(questionId: Int, answer: String): Flow<Unit> = flow {
        val response = surveyAPI.submitAnswer(
            answer = AnswerData(id = questionId, answer = answer)
        )
        emit(response)
    }
}