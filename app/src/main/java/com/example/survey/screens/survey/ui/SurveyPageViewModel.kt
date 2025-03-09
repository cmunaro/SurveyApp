package com.example.survey.screens.survey.ui

import androidx.lifecycle.viewModelScope
import com.example.survey.screens.survey.domain.GetQuestionsUseCase
import com.example.survey.screens.survey.domain.SubmitAnswerUseCase
import com.example.survey.screens.survey.domain.model.toQuestions
import com.example.survey.utils.Async
import com.example.survey.utils.StateViewModel
import com.example.survey.utils.getOrThrow
import com.example.survey.utils.mapValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlin.time.Duration.Companion.seconds

@OptIn(ExperimentalCoroutinesApi::class)
class SurveyPageViewModel(
    private val getQuestionsUseCase: GetQuestionsUseCase,
    private val submitAnswerUseCase: SubmitAnswerUseCase
) : StateViewModel<SurveyPageState>(
    initialValue = SurveyPageState()
) {
    private var submitJobs: MutableMap<Int, Job> = mutableMapOf()

    init {
        loadQuestions()
    }

    private fun loadQuestions() {
        getQuestionsUseCase()
            .map { domainQuestions -> Async.Success(domainQuestions.toQuestions()) }
            .catch<Async<List<Question>>> { emit(Async.Failure(it)) }
            .onStart { Async.Loading(null) }
            .onEach { updateState { copy(asyncQuestions = it) } }
            .launchIn(viewModelScope)
    }

    fun onAnswerChange(questionId: Int, newAnswer: String) {
        updateState {
            copy(
                asyncQuestions = asyncQuestions.mapValue { questions ->
                    questions.map { question ->
                        if (question.id == questionId) {
                            question.copy(answer = Answer(newAnswer))
                        } else question
                    }
                }
            )
        }
    }

    fun onAnswerSubmit(questionId: Int) {
        submitJobs[questionId]?.cancel()
        submitJobs[questionId] = flow {
            val question = state.value.asyncQuestions.getOrThrow()
                .first { it.id == questionId }
            emit(question)
        }.flatMapConcat { question ->
            submitAnswerUseCase(questionId = question.id, answer = question.answer.value)
        }.onEach {
            updateQuestionSubmissionState(
                questionId = questionId,
                submissionState = Async.Success(true),
                submissionAlert = SubmissionAlert.SUCCESS
            )
        }.catch {
            updateQuestionSubmissionState(
                questionId = questionId,
                submissionState = Async.Success(false),
                submissionAlert = SubmissionAlert.FAILURE
            )
            emit(Unit)
        }.onEach {
            delay(3.seconds)
            updateQuestionSubmissionState(
                questionId = questionId,
                submissionAlert = SubmissionAlert.NONE
            )
        }.launchIn(viewModelScope)
    }

    private fun updateQuestionSubmissionState(
        questionId: Int,
        submissionState: Async<Boolean>? = null,
        submissionAlert: SubmissionAlert? = null
    ) {
        updateState {
            copy(
                asyncQuestions = asyncQuestions.mapValue { questions ->
                    questions.map { question ->
                        if (question.id == questionId) {
                            question.copy(
                                submitted = submissionState ?: question.submitted,
                                submissionAlert = submissionAlert ?: question.submissionAlert
                            )
                        } else question
                    }
                }
            )
        }
    }
}