package com.example.survey.screens.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.survey.ui.theme.SurveyTheme

@Composable
fun MainPage(onGoToSurvey: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(
            modifier = Modifier.align(Alignment.TopCenter),
            text = "Welcome",
            style = MaterialTheme.typography.headlineMedium
        )

        Button(
            modifier = Modifier.align(Alignment.Center),
            onClick = onGoToSurvey
        ) {
            Text(
                text = "Start Survey",
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MainPagePreview() {
    SurveyTheme {
        MainPage(onGoToSurvey = {})
    }
}
