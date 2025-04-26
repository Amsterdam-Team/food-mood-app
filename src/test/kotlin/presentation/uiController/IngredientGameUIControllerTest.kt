package presentation.uiController

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import logic.usecase.ingredient_game_usecases.StartIngredientGameUseCase
import logic.usecase.ingredient_game_usecases.SubmitAnswerUseCase
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import presentation.uiController.helpers.IngredientMealsTestFactory
import java.io.ByteArrayOutputStream
import java.io.PrintStream
import kotlin.test.assertTrue

class IngredientGameUIControllerTest {

    private val outputStreamCaptor = ByteArrayOutputStream()
    private val originalOut = System.out

    private lateinit var startIngredientGameUseCase: StartIngredientGameUseCase
    private lateinit var submitAnswerUseCase: SubmitAnswerUseCase
    private lateinit var ingredientGameUIController: IngredientGameUIController

    @BeforeEach
    fun setUp() {
        System.setOut(PrintStream(outputStreamCaptor))
        startIngredientGameUseCase = mockk()
        submitAnswerUseCase = mockk()
        ingredientGameUIController = IngredientGameUIController(startIngredientGameUseCase, submitAnswerUseCase)
    }

    @AfterEach
    fun tearDown() {
        System.setOut(originalOut)
    }

    @Test
    fun `should call startGame when execute is called`() {
        every { startIngredientGameUseCase.startGame(any()) } returns IngredientMealsTestFactory.gameInProgress() andThen IngredientMealsTestFactory.gameFinished()
        every { submitAnswerUseCase.submit(any(), any(), any()) } returns true

        IngredientMealsTestFactory.simulateUserInputs("1").use {
            ingredientGameUIController.execute()
        }

        verify { startIngredientGameUseCase.startGame(any()) }
    }

    @Test
    fun `should call submitAnswer when user selects an answer`() {
        every { startIngredientGameUseCase.startGame(any()) } returns IngredientMealsTestFactory.gameInProgress() andThen IngredientMealsTestFactory.gameFinished()
        every { submitAnswerUseCase.submit(any(), any(), any()) } returns true

        IngredientMealsTestFactory.simulateUserInputs("1").use {
            ingredientGameUIController.execute()
        }

        verify {
            submitAnswerUseCase.submit(
                userAnswer = "Cheese",
                correctAnswer = "Cheese",
                gameState = any()
            )
        }
    }

    @Test
    fun `should print congratulations when game is finished`() {
        every { startIngredientGameUseCase.startGame(any()) } returns IngredientMealsTestFactory.gameFinished()

        ingredientGameUIController.execute()

        val output = outputStreamCaptor.toString()
        assertTrue(output.contains("Congratulations"))
        assertTrue(output.contains("15000"))
    }

    @Test
    fun `should print Correct when submitted answer is true`() {
        every { startIngredientGameUseCase.startGame(any()) } returns IngredientMealsTestFactory.gameInProgress() andThen IngredientMealsTestFactory.gameFinished()
        every { submitAnswerUseCase.submit(any(), any(), any()) } returns true

        IngredientMealsTestFactory.simulateUserInputs("1").use {
            ingredientGameUIController.execute()
        }

        val output = outputStreamCaptor.toString()
        assertTrue(output.contains("Correct!"))
        assertTrue(output.contains("Thank you for playing"))
    }

    @Test
    fun `should print Game Over when submitted answer is false`() {
        every { startIngredientGameUseCase.startGame(any()) } returns IngredientMealsTestFactory.gameInProgress()
        every { submitAnswerUseCase.submit("Tomato", "Cheese", any()) } returns false

        IngredientMealsTestFactory.simulateUserInputs("2").use {
            ingredientGameUIController.execute()
        }

        val output = outputStreamCaptor.toString()
        assertTrue(output.contains("Game Over"))
    }

    @Test
    fun `should retry input when user enters invalid input then valid input during game execution`() {
        every { startIngredientGameUseCase.startGame(any()) } returns IngredientMealsTestFactory.gameInProgress() andThen IngredientMealsTestFactory.gameFinished()
        every { submitAnswerUseCase.submit(any(), any(), any()) } returns true

        IngredientMealsTestFactory.simulateUserInputs("99", "1").use {
            ingredientGameUIController.execute()
        }

        val output = outputStreamCaptor.toString()
        assertTrue(output.contains("Invalid input. Please enter a number from 1 to 3"))
    }
}