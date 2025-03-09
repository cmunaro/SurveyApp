package com.example.survey.di

import com.example.survey.screens.survey.ui.SurveyPageViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val viewModelModule = module {
    viewModelOf(::SurveyPageViewModel)
}