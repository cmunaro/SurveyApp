package com.example.survey.screens.survey.ui

import com.example.survey.utils.Async
import io.kotest.matchers.shouldBe
import org.junit.Test

class SurveyPageStateTest {
    @Test
    fun `when asyncQuestions is Success and there are mixed values, numberOfSubmittedQuestions returns a correct value`() {
        val state = SurveyPageState(
            asyncQuestions = Async.Success(
                listOf(
                    Question(id = 0, query = Query(""), submitted = Async.Success(true)),
                    Question(id = 1, query = Query(""), submitted = Async.Success(false)),
                    Question(id = 2, query = Query(""), submitted = Async.Success(true)),
                    Question(id = 3, query = Query(""), submitted = Async.Loading(null)),
                    Question(id = 4, query = Query(""), submitted = Async.Uninitialized),
                    Question(id = 5, query = Query(""), submitted = Async.Failure(Exception())),
                )
            )
        )

        state.numberOfSubmittedQuestions shouldBe 2
    }

    @Test
    fun `when asyncQuestion is Uninitialized, numberOfSubmittedQuestions is 0`() {
        val state = SurveyPageState(asyncQuestions = Async.Uninitialized)

        state.numberOfSubmittedQuestions shouldBe 0
    }

    @Test
    fun `when asyncQuestion is Loading and empty, numberOfSubmittedQuestions is 0`() {
        val state = SurveyPageState(asyncQuestions = Async.Loading(null))

        state.numberOfSubmittedQuestions shouldBe 0
    }

    @Test
    fun `when asyncQuestion is Failure, numberOfSubmittedQuestions is 0`() {
        val state = SurveyPageState(asyncQuestions = Async.Failure(Exception()))

        state.numberOfSubmittedQuestions shouldBe 0
    }
}