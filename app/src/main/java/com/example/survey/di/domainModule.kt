package com.example.survey.di

import com.example.survey.screens.survey.domain.GetQuestionsUseCase
import com.example.survey.screens.survey.domain.GetQuestionsUseCaseImpl
import com.example.survey.screens.survey.domain.SubmitAnswerUseCase
import com.example.survey.screens.survey.domain.SubmitAnswerUseCaseImpl
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module

val domainModule = module {
    factoryOf(::GetQuestionsUseCaseImpl) bind GetQuestionsUseCase::class
    factoryOf(::SubmitAnswerUseCaseImpl) bind SubmitAnswerUseCase::class
}