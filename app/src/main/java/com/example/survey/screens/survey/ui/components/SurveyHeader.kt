package com.example.survey.screens.survey.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun SurveyHeader(
    currentQuestion: Int,
    numberOfQuestions: Int,
    numberOfSubmittedQuestions: Int,
    onPrevious: () -> Unit,
    onNext: () -> Unit
) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Question $currentQuestion/$numberOfQuestions",
                style = MaterialTheme.typography.headlineSmall
            )

            Spacer(modifier = Modifier.weight(1f))

            CompositionLocalProvider(
                LocalTextStyle provides MaterialTheme.typography.titleMedium
            ) {
                Button(
                    onClick = onPrevious,
                    enabled = currentQuestion > 1
                ) {
                    Text(text = "Previous")
                }

                Button(
                    onClick = onNext,
                    enabled = currentQuestion < numberOfQuestions
                ) {
                    Text(text = "Next")
                }
            }
        }

        Text(
            text = "Questions submitted: $numberOfSubmittedQuestions",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SurveyHeaderPreviewFirstQuestion() {
    SurveyHeader(
        currentQuestion = 1,
        numberOfQuestions = 10,
        numberOfSubmittedQuestions = 5,
        onPrevious = {},
        onNext = {}
    )
}

@Preview(showBackground = true)
@Composable
private fun SurveyHeaderPreviewLastQuestion() {
    SurveyHeader(
        currentQuestion = 10,
        numberOfQuestions = 10,
        numberOfSubmittedQuestions = 5,
        onPrevious = {},
        onNext = {}
    )
}
