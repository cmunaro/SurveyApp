package com.example.survey.screens.survey.domain.model

import com.example.survey.screens.survey.ui.Query
import com.example.survey.screens.survey.ui.Question
import io.kotest.matchers.shouldBe
import org.junit.Test

class MapperTest {
    @Test
    fun `map List of QuestionDomain to List of Question`() {
        dummyQuestionsDomain.toQuestions() shouldBe expectedQuestions
    }

    companion object {
        private val dummyQuestionsDomain = listOf(
            QuestionDomain(
                id = 1,
                query = "A"
            ),
            QuestionDomain(
                id = 2,
                query = "B"
            )
        )
        private val expectedQuestions = listOf(
            Question(
                id = 1,
                query = Query("A")
            ),
            Question(
                id = 2,
                query = Query("B")
            )
        )
    }
}