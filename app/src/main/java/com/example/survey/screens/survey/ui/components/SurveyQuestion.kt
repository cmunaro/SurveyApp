package com.example.survey.screens.survey.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Button
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.survey.R
import com.example.survey.screens.survey.ui.Answer
import com.example.survey.screens.survey.ui.Query
import com.example.survey.screens.survey.ui.Question
import com.example.survey.screens.survey.ui.SubmissionAlert
import com.example.survey.utils.Async
import com.example.survey.utils.getOrElse
import com.example.survey.utils.getOrNull

@Composable
fun SurveyQuestion(
    modifier: Modifier = Modifier,
    question: Question,
    onAnswerChange: (newAnswer: String) -> Unit,
    onAnswerSubmit: () -> Unit
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(48.dp)
    ) {
        val alertHeight by animateDpAsState(
            targetValue = if (question.submissionAlert != SubmissionAlert.NONE) 200.dp else 0.dp,
            label = "alert size"
        )
        val alertColor by animateColorAsState(
            targetValue = when (question.submissionAlert) {
                SubmissionAlert.SUCCESS -> Color.Green.copy(alpha = .5f)
                SubmissionAlert.FAILURE -> Color.Red.copy(alpha = .6f)
                else -> MaterialTheme.colorScheme.background
            },
            label = "alert color"
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(alertHeight)
                .background(color = alertColor)
                .padding(horizontal = 32.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.weight(1f),
                text = when (question.submissionAlert) {
                    SubmissionAlert.SUCCESS -> stringResource(R.string.success)
                    SubmissionAlert.FAILURE -> stringResource(R.string.failure)
                    else -> ""
                },
                style = MaterialTheme.typography.headlineMedium.copy()
            )

            if (question.submissionAlert == SubmissionAlert.FAILURE) {
                Button(onClick = onAnswerSubmit) {
                    Text(
                        text = "Retry",
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
        }

        Text(
            modifier = Modifier.padding(horizontal = 32.dp),
            text = question.query.value,
            style = MaterialTheme.typography.headlineMedium
        )

        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp),
            value = question.answer.value,
            enabled = question.submitted.getOrNull() == false,
            onValueChange = onAnswerChange,
            placeholder = {
                Text(text = stringResource(R.string.type_here_for_an_answer))
            }
        )

        Button(
            onClick = onAnswerSubmit,
            enabled = question.submissionAlert == SubmissionAlert.NONE &&
                    question.submitted.getOrNull() == false &&
                    question.answer.value.isNotBlank(),
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentWidth()
        ) {
            CompositionLocalProvider(
                LocalTextStyle provides MaterialTheme.typography.titleMedium
            ) {
                if (question.submitted.getOrElse(false)) {
                    Text(text = stringResource(R.string.already_submitted))
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
            id = 1,
            query = Query("What is your name?"),
            answer = Answer("John Doe"),
            submitted = Async.Success(true)
        ),
        onAnswerChange = {},
        onAnswerSubmit = {}
    )
}

@Preview(showBackground = true)
@Composable
private fun SurveyQuestionPreviewUnsubmitted() {
    SurveyQuestion(
        question = Question(
            id = 1,
            query = Query("What is your name?")
        ),
        onAnswerChange = {},
        onAnswerSubmit = {}
    )
}

@Preview(showBackground = true)
@Composable
private fun SurveyQuestionPreviewSubmittedErrorAlert() {
    SurveyQuestion(
        question = Question(
            id = 1,
            query = Query("What is your name?"),
            answer = Answer("John Doe"),
            submissionAlert = SubmissionAlert.FAILURE
        ),
        onAnswerChange = {},
        onAnswerSubmit = {}
    )
}

@Preview(showBackground = true)
@Composable
private fun SurveyQuestionPreviewSubmittedSuccessAlert() {
    SurveyQuestion(
        question = Question(
            id = 1,
            query = Query("What is your name?"),
            answer = Answer("John Doe"),
            submitted = Async.Success(true),
            submissionAlert = SubmissionAlert.SUCCESS
        ),
        onAnswerChange = {},
        onAnswerSubmit = {}
    )
}