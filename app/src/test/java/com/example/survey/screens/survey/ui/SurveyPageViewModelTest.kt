package com.example.survey.screens.survey.ui

import app.cash.turbine.test
import com.example.survey.MainDispatcherRule
import com.example.survey.screens.survey.domain.FakeGetQuestionsUseCase
import com.example.survey.screens.survey.domain.FakeSubmitAnswerUseCase
import com.example.survey.screens.survey.domain.model.toQuestions
import com.example.survey.utils.Async
import io.kotest.matchers.shouldBe
import io.mockk.coVerify
import io.mockk.spyk
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test

class SurveyPageViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun `on init load questions`() = runTest {
        val viewModel = SurveyPageViewModel(
            getQuestionsUseCase = FakeGetQuestionsUseCase(),
            submitAnswerUseCase = FakeSubmitAnswerUseCase()
        )

        viewModel.state.value shouldBe SurveyPageState(
            Async.Success(FakeGetQuestionsUseCase.questions.toQuestions())
        )
    }

    @Test
    fun `on init fails to load questions`() = runTest {
        val viewModel = SurveyPageViewModel(
            getQuestionsUseCase = FakeGetQuestionsUseCase(shouldFail = true),
            submitAnswerUseCase = FakeSubmitAnswerUseCase()
        )

        viewModel.state.value shouldBe SurveyPageState(
            Async.Failure(FakeGetQuestionsUseCase.exception)
        )
    }

    @Test
    fun `onAnswerChange should update only that specific answer`() = runTest {
        val newAnswer = "New Answer"
        val viewModel = SurveyPageViewModel(
            getQuestionsUseCase = FakeGetQuestionsUseCase(),
            submitAnswerUseCase = FakeSubmitAnswerUseCase()
        )

        viewModel.onAnswerChange(
            questionId = FakeGetQuestionsUseCase.questions.first().id,
            newAnswer = newAnswer
        )
        viewModel.state.value shouldBe SurveyPageState(
            Async.Success(
                getAlteredQuestions(
                    questionId = FakeGetQuestionsUseCase.questions.first().id,
                    newAnswer = newAnswer
                )
            )
        )
    }

    @Test
    fun asd() = runTest {
        val newAnswer = "New Answer"
        val spiedSubmitAnswerUseCase = spyk(FakeSubmitAnswerUseCase())
        val viewModel = SurveyPageViewModel(
            getQuestionsUseCase = FakeGetQuestionsUseCase(),
            submitAnswerUseCase = spiedSubmitAnswerUseCase
        )
        viewModel.state.test {
            // Change the first answer
            viewModel.onAnswerChange(
                questionId = FakeGetQuestionsUseCase.questions.first().id,
                newAnswer = newAnswer
            )
            // Submit the first answer
            viewModel.onAnswerSubmit(
                questionId = FakeGetQuestionsUseCase.questions.first().id
            )
            awaitItem() // Initial state
            awaitItem() // State after onAnswerChange

            coVerify(exactly = 1) {
                spiedSubmitAnswerUseCase.invoke(
                    questionId = FakeGetQuestionsUseCase.questions.first().id,
                    answer = newAnswer
                )
            }
            // Should set success state
            awaitItem() shouldBe SurveyPageState(
                Async.Success(
                    getAlteredQuestions(
                        newAnswer = newAnswer,
                        questionId = FakeGetQuestionsUseCase.questions.first().id,
                        submitted = Async.Success(true),
                        submissionAlert = SubmissionAlert.SUCCESS
                    )
                )
            )
            // Should reset submissionAlert
            awaitItem() shouldBe SurveyPageState(
                Async.Success(
                    getAlteredQuestions(
                        newAnswer = newAnswer,
                        questionId = FakeGetQuestionsUseCase.questions.first().id,
                        submitted = Async.Success(true),
                        submissionAlert = SubmissionAlert.NONE
                    )
                )
            )
        }
    }

    private fun getAlteredQuestions(
        questionId: Int,
        newAnswer: String? = null,
        submitted: Async<Boolean>? = null,
        submissionAlert: SubmissionAlert? = null
    ): List<Question> =
        FakeGetQuestionsUseCase.questions.toQuestions().map {
            if (it.id == questionId) {
                it.copy(
                    answer = Answer(newAnswer ?: it.answer.value),
                    submitted = submitted ?: it.submitted,
                    submissionAlert = submissionAlert ?: it.submissionAlert
                )
            } else it
        }
}