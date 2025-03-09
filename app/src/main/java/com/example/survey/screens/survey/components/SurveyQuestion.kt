package com.example.survey.screens.survey.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Button
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.survey.screens.survey.Answer
import com.example.survey.screens.survey.Query
import com.example.survey.screens.survey.Question

@Composable
fun SurveyQuestion(
    modifier: Modifier = Modifier,
    question: Question,
    canSubmit: Boolean,
    onAnswerChange: (newAnswer: String) -> Unit,
    onAnswerSubmit: () -> Unit
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(48.dp)
    ) {
        Text(
            text = question.query.value,
            style = MaterialTheme.typography.headlineMedium
        )

        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = question.answer.value,
            enabled = question.submitted.not(),
            onValueChange = onAnswerChange,
            placeholder = {
                Text(text = "Type here for an answer")
            }
        )

        Button(
            onClick = onAnswerSubmit,
            enabled = question.submitted.not() and canSubmit,
            modifier = Modifier.fillMaxWidth()
                .wrapContentWidth()
        ) {
            CompositionLocalProvider(
                LocalTextStyle provides MaterialTheme.typography.titleMedium
            ) {
                if (question.submitted) {
                    Text(text = "Already submitted")
                } else {
                    Text(text = "Submit")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SurveyQuestionPreviewSubmitted() {
    SurveyQuestion(
        question = Question(
            id = "1",
            query = Query("What is your name?"),
            answer = Answer("John Doe"),
            submitted = true
        ),
        canSubmit = true,
        onAnswerChange = {},
        onAnswerSubmit = {}
    )
}

@Preview(showBackground = true)
@Composable
private fun SurveyQuestionPreviewUnsubmitted() {
    SurveyQuestion(
        question = Question(
            id = "1",
            query = Query("What is your name?")
        ),
        canSubmit = true,
        onAnswerChange = {},
        onAnswerSubmit = {}
    )
}

@Preview(showBackground = true)
@Composable
private fun SurveyQuestionPreviewUnsubmittedCantSubmit() {
    SurveyQuestion(
        question = Question(
            id = "1",
            query = Query("What is your name?")
        ),
        canSubmit = false,
        onAnswerChange = {},
        onAnswerSubmit = {}
    )
}
