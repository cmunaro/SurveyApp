package com.example.survey.screens.survey

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import kotlinx.serialization.Serializable

@Serializable
data object SurveyPageRoute

@Composable
fun SurveyPage() {
    Text("Hello world")
}