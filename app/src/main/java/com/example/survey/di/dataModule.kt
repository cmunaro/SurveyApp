package com.example.survey.di

import com.example.survey.screens.survey.data.MockedSurveyAPI
import com.example.survey.screens.survey.data.SurveyAPI
import com.example.survey.screens.survey.data.SurveyRepositoryImpl
import com.example.survey.screens.survey.domain.SurveyRepository
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val dataModule = module {
    single<SurveyRepository> { SurveyRepositoryImpl(surveyAPI = get()) }
    singleOf(::MockedSurveyAPI) bind SurveyAPI::class
}