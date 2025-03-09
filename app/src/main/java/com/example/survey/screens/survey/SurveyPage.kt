package com.example.survey.screens.survey

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.survey.screens.survey.components.SurveyHeader
import com.example.survey.screens.survey.components.SurveyQuestion
import com.example.survey.utils.Async
import kotlinx.serialization.Serializable
import org.koin.compose.viewmodel.koinViewModel

@Serializable
data object SurveyPageRoute

@Composable
fun SurveyPage(viewmodel: SurveyPageViewModel = koinViewModel()) {
    val state by viewmodel.state.collectAsState()

    SurveyScreen(
        state = state,
        onQuestionFailure = {}
    )
}

@Composable
fun SurveyScreen(
    state: SurveyPageState,
    onQuestionFailure: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        when (state.asyncQuestions) {
            is Async.Failure -> onQuestionFailure()
            is Async.Loading, Async.Uninitialized -> CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )

            is Async.Success -> {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(64.dp)
                ) {
                    val pagerState = rememberPagerState { state.asyncQuestions.value.size }

                    SurveyHeader(
                        currentQuestion = pagerState.currentPage + 1,
                        numberOfQuestions = pagerState.pageCount,
                        numberOfSubmittedQuestions = state.numberOfSubmittedQuestions,
                        onPrevious = {},
                        onNext = {}
                    )

                    HorizontalPager(
                        state = pagerState
                    ) { index ->
                        SurveyQuestion(
                            question = state.asyncQuestions.value[index],
                            canSubmit = state.submission == Submission.IDLE,
                            onAnswerChange = {},
                            onAnswerSubmit = {}
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SurveyScreenPreviewLoading() {
    SurveyScreen(
        state = SurveyPageState(
            asyncQuestions = Async.Loading(null)
        ),
        onQuestionFailure = {}
    )
}

@Preview(showBackground = true)
@Composable
private fun SurveyScreenPreviewSuccess() {
    SurveyScreen(
        state = SurveyPageState(
            asyncQuestions = Async.Success(
                listOf(
                    Question(
                        id = "1",
                        query = Query("What is your name?")
                    ),
                    Question(
                        id = "2",
                        query = Query("What is your feet size?")
                    ),
                )
            )
        ),
        onQuestionFailure = {}
    )
}
