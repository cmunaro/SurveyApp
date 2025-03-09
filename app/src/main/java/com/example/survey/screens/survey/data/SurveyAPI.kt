package com.example.survey.screens.survey.data

import com.example.survey.screens.survey.data.model.QuestionData
import kotlinx.coroutines.delay
import retrofit2.http.GET

interface SurveyAPI {
    @GET("/questions")
    suspend fun fetchQuestions(): List<QuestionData>
}

class MockedSurveyAPI : SurveyAPI {
    override suspend fun fetchQuestions(): List<QuestionData> {
        delay(1000)
        return listOf(
            QuestionData(1, "What is your favourite colour?"),
            QuestionData(2, "What is your favourite food?"),
            QuestionData(3, "What is your favourite country?"),
            QuestionData(4, "What is your favourite sport?"),
            QuestionData(5, "What is your favourite team?"),
            QuestionData(6, "What is your favourite programming language?"),
            QuestionData(7, "What is your favourite song?"),
            QuestionData(8, "What is your favourite band?"),
            QuestionData(9, "What is your favourite music?"),
            QuestionData(10, "What is your favourite brand?")
        )
    }
}
