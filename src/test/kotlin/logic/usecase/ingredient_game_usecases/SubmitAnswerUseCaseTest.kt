package logic.usecase.ingredient_game_usecases

import com.google.common.truth.Truth.assertThat
import logic.models.IngredientGameState
import org.junit.jupiter.api.BeforeEach
import presentation.uiController.helpers.IngredientMealsTestFactory
import kotlin.test.Test

class SubmitAnswerUseCaseTest{
 private lateinit var gameState: IngredientGameState
 private lateinit var submitAnswerUseCase: SubmitAnswerUseCase

 @BeforeEach
 fun setUp() {
  gameState = IngredientMealsTestFactory.newGameState()
  submitAnswerUseCase = SubmitAnswerUseCase()
 }

 @Test
 fun `should return true and increase score when answer is correct`() {
  // Given
  val correctAnswer = "Tomato"
  val userAnswer = "Tomato"

  // When
  val isCorrect = submitAnswerUseCase.submit(userAnswer, correctAnswer, gameState)

  // Then
  assertThat(isCorrect).isTrue()
  assertThat(gameState.score).isGreaterThan(0)
 }

 @Test
 fun `should return false and not increase score when answer is incorrect`() {
  // Given
  val correctAnswer = "Tomato"
  val userAnswer = "Cucumber"

  // When
  val isCorrect = submitAnswerUseCase.submit(userAnswer, correctAnswer, gameState)

  // Then
  assertThat(isCorrect).isFalse()
  assertThat(gameState.score).isEqualTo(0)
 }
}