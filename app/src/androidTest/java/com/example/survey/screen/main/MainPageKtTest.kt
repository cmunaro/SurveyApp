package com.example.survey.screen.main

import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.survey.screens.main.MainPage
import io.mockk.spyk
import io.mockk.verify
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainPageKtTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun mainPageIsDisplayedAndStartSurveyIsWorking() {
        val mockedLambda: () -> Unit = spyk({})
        composeTestRule.setContent {
            MainPage(onGoToSurvey = mockedLambda)
        }

        composeTestRule.onNodeWithText("Welcome").isDisplayed()
        composeTestRule.onNodeWithText("Start Survey").performClick()
        verify(exactly = 1) { mockedLambda.invoke() }
    }
}