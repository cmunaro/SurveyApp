package com.example.survey.di

import com.example.survey.screens.survey.data.SurveyAPI
import com.example.survey.screens.survey.data.SurveyRepositoryImpl
import com.example.survey.screens.survey.domain.SurveyRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create


val dataModule = module {
    single<SurveyRepository> { SurveyRepositoryImpl(surveyAPI = get()) }
//    singleOf(::MockedSurveyAPI) bind SurveyAPI::class
    single<SurveyAPI> {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor).build()

        Retrofit.Builder()
            .client(client)
            .baseUrl("https://xm-assignment.web.app")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create<SurveyAPI>()
    }
}