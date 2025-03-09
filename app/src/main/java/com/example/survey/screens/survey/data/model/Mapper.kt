package com.example.survey.screens.survey.data.model

import com.example.survey.screens.survey.domain.model.QuestionDomain

fun FetchQuestionsResponse.toQuestionsDomain() = value.map { questionData ->
    QuestionDomain(questionData.id, questionData.question)
}
