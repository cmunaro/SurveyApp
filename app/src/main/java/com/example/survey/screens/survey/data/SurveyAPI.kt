package com.example.survey.screens.survey.data

import com.example.survey.screens.survey.data.model.AnswerData
import com.example.survey.screens.survey.data.model.QuestionData
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface SurveyAPI {
    @GET("/questions")
    suspend fun fetchQuestions(): List<QuestionData>

    @POST("/question/submit")
    suspend fun submitAnswer(
        @Body answer: AnswerData
    )
}
