package com.example.survey.screens.survey.ui.components

import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.survey.screens.survey.ui.Answer
import com.example.survey.screens.survey.ui.Query
import com.example.survey.screens.survey.ui.Question
import com.example.survey.screens.survey.ui.SubmissionAlert
import com.example.survey.utils.Async
import io.mockk.spyk
import io.mockk.verify
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SurveyQuestionKtTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun changeAndSubmitAnswer() {
        val onAnswerChange: (newAnswer: String) -> Unit = spyk({})
        val onAnswerSubmit: () -> Unit = spyk({})
        composeTestRule.setContent {
            SurveyQuestion(
                question = Question(
                    id = 1,
                    query = Query("What is your name?"),
                    answer = Answer("John Doe")
                ),
                onAnswerChange = onAnswerChange,
                onAnswerSubmit = onAnswerSubmit
            )
        }

        composeTestRule.onNodeWithText("What is your name?").isDisplayed()
        composeTestRule.onNodeWithText("John Doe").isDisplayed()
        composeTestRule.onNodeWithText("John Doe").performTextInput("a")
        composeTestRule.onNodeWithText("Submit").performClick()
        verify(exactly = 1) { onAnswerChange.invoke(eq("aJohn Doe")) }
        verify(exactly = 1) { onAnswerSubmit.invoke() }
    }

    @Test
    fun displayAnAlreadySubmittedQuestion() {
        composeTestRule.setContent {
            SurveyQuestion(
                question = Question(
                    id = 1,
                    query = Query("What is your name?"),
                    answer = Answer("John Doe"),
                    submitted = Async.Success(true)
                ),
                onAnswerChange = { },
                onAnswerSubmit = { }
            )
        }

        composeTestRule.onNodeWithText("What is your name?").isDisplayed()
        composeTestRule.onNodeWithText("John Doe").isDisplayed()
        composeTestRule.onNodeWithText("John Doe").assertIsNotEnabled()
        composeTestRule.onNodeWithText("Already submitted").isDisplayed()
        composeTestRule.onNodeWithText("Already submitted").assertIsNotEnabled()
    }

    @Test
    fun displayErrorAlertAndRetry() {
        val onAnswerSubmit: () -> Unit = spyk({})
        composeTestRule.setContent {
            SurveyQuestion(
                question = Question(
                    id = 1,
                    query = Query("What is your name?"),
                    answer = Answer("John Doe"),
                    submitted = Async.Loading(null),
                    submissionAlert = SubmissionAlert.FAILURE
                ),
                onAnswerChange = { },
                onAnswerSubmit = onAnswerSubmit
            )
        }

        composeTestRule.onNodeWithText("Failure").isDisplayed()
        composeTestRule.onNodeWithText("Retry").isDisplayed()
        composeTestRule.onNodeWithText("Retry").performClick()
        verify(exactly = 1) { onAnswerSubmit.invoke() }
    }

    @Test
    fun displaySuccessAlert() {
        composeTestRule.setContent {
            SurveyQuestion(
                question = Question(
                    id = 1,
                    query = Query("What is your name?"),
                    answer = Answer("John Doe"),
                    submitted = Async.Success(true),
                    submissionAlert = SubmissionAlert.SUCCESS
                ),
                onAnswerChange = { },
                onAnswerSubmit = { }
            )
        }

        composeTestRule.onNodeWithText("Success").isDisplayed()
        composeTestRule.onNodeWithText("Retry").assertDoesNotExist()
        composeTestRule.onNodeWithText("John Doe").assertIsNotEnabled()
        composeTestRule.onNodeWithText("Already submitted").assertIsNotEnabled()
    }

}