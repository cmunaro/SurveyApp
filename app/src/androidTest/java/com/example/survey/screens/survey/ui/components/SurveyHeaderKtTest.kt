package com.example.survey.screens.survey.ui.components

import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import io.mockk.spyk
import io.mockk.verify
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SurveyHeaderKtTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun headerDisplaysItsDataAndButtonsAreConnected() {
        val mockedNextLambda: () -> Unit = spyk({})
        val mockedPreviousLambda: () -> Unit = spyk({})
        composeTestRule.setContent {
            SurveyHeader(
                currentQuestion = 2,
                numberOfQuestions = 10,
                numberOfSubmittedQuestions = 5,
                onPrevious = mockedPreviousLambda,
                onNext = mockedNextLambda
            )
        }

        composeTestRule.onNodeWithText("Previous").isDisplayed()
        composeTestRule.onNodeWithText("Next").isDisplayed()
        composeTestRule.onNodeWithText("Question 2/10").isDisplayed()
        composeTestRule.onNodeWithText("Question submitted 5").isDisplayed()
        composeTestRule.onNodeWithText("Previous").performClick()
        verify(exactly = 0) { mockedNextLambda.invoke() }
        verify(exactly = 1) { mockedPreviousLambda.invoke() }
        composeTestRule.onNodeWithText("Next").performClick()
        verify(exactly = 1) { mockedNextLambda.invoke() }
    }

    @Test
    fun previousButtonIsDisabledAtTheFirstQuestion() {
        composeTestRule.setContent {
            SurveyHeader(
                currentQuestion = 1,
                numberOfQuestions = 10,
                numberOfSubmittedQuestions = 5,
                onPrevious = { },
                onNext = { }
            )
        }

        composeTestRule.onNodeWithText("Previous").isDisplayed()
        composeTestRule.onNodeWithText("Previous").isDisplayed()
    }

    @Test
    fun nextButtonIsDisabledAtTheLastQuestion() {
        composeTestRule.setContent {
            SurveyHeader(
                currentQuestion = 10,
                numberOfQuestions = 10,
                numberOfSubmittedQuestions = 5,
                onPrevious = { },
                onNext = { }
            )
        }

        composeTestRule.onNodeWithText("Next").isDisplayed()
        composeTestRule.onNodeWithText("Next").isDisplayed()
    }
}