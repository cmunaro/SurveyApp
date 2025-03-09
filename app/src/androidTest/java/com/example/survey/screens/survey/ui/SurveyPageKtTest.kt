package com.example.survey.screens.survey.ui

import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.survey.utils.Async
import io.mockk.spyk
import io.mockk.verify
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SurveyPageKtTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun displayLoader() {
        composeTestRule.setContent {
            SurveyScreen(
                state = SurveyPageState(
                    asyncQuestions = Async.Loading(null)
                ),
                onQuestionFailure = { },
                onAnswerChange = { _, _ -> },
                onAnswerSubmit = { }
            )
        }

        composeTestRule.onNodeWithTag("Loader").isDisplayed()
    }

    @Test
    fun triggerFatalFailure() {
        val mockedLambda: () -> Unit = spyk({})
        composeTestRule.setContent {
            SurveyScreen(
                state = SurveyPageState(
                    asyncQuestions = Async.Failure(Throwable())
                ),
                onQuestionFailure = mockedLambda,
                onAnswerChange = { _, _ -> },
                onAnswerSubmit = { }
            )
        }

        verify(exactly = 1) { mockedLambda.invoke() }
    }

    @Test
    fun displayQuestionsAndSubmitAnswer() {
        val onAnswerChange: (id: Int, newAnswer: String) -> Unit = spyk({ _, _ -> })
        val onAnswerSubmit: (id: Int) -> Unit = spyk({})
        composeTestRule.setContent {
            SurveyScreen(
                state = SurveyPageState(
                    asyncQuestions = Async.Success(
                        List(2) {
                            Question(
                                id = it,
                                query = Query("Question $it"),
                                answer = Answer("Answer $it")
                            )
                        }
                    )
                ),
                onQuestionFailure = { },
                onAnswerChange = onAnswerChange,
                onAnswerSubmit = onAnswerSubmit
            )
        }

        composeTestRule.onNodeWithText("Question 0").isDisplayed()
        composeTestRule.onNodeWithText("Answer 0").performTextInput("a")
        composeTestRule.onNodeWithText("Submit").performClick()
        verify(exactly = 1) { onAnswerChange.invoke(eq(0), eq("aAnswer 0")) }
        verify(exactly = 1) { onAnswerSubmit.invoke(eq(0)) }
    }

    @Test
    fun displayFirstAndSecondQuestions() {
        val onAnswerChange: (id: Int, newAnswer: String) -> Unit = spyk({ _, _ -> })
        val onAnswerSubmit: (id: Int) -> Unit = spyk({})
        composeTestRule.setContent {
            SurveyScreen(
                state = SurveyPageState(
                    asyncQuestions = Async.Success(
                        List(2) {
                            Question(id = it, query = Query("Question $it"))
                        }
                    )
                ),
                onQuestionFailure = { },
                onAnswerChange = onAnswerChange,
                onAnswerSubmit = onAnswerSubmit
            )
        }

        composeTestRule.onNodeWithText("Question 0").isDisplayed()
        composeTestRule.onNodeWithText("Previous").assertIsNotEnabled()
        composeTestRule.onNodeWithText("Next").isDisplayed()
        composeTestRule.onNodeWithText("Next").performClick()
        composeTestRule.onNodeWithText("Next").assertIsNotEnabled()
        composeTestRule.onNodeWithText("Question 1").isDisplayed()
    }
}