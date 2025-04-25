package presentation.uiController

import io.mockk.mockk
import io.mockk.verify
import logic.usecase.GuessPreparationTimeUseCase
import logic.usecase.GuessPreparationTimeUseCase.ComparisonResult.Close
import logic.usecase.GuessPreparationTimeUseCase.ComparisonResult.Correct
import logic.usecase.GuessPreparationTimeUseCase.ComparisonResult.TooHigh
import logic.usecase.GuessPreparationTimeUseCase.ComparisonResult.TooLow
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import presentation.console.ConsoleIO
import presentation.uiController.helpers.GuessGameTestHelper.givenMultipleGuesses
import presentation.uiController.helpers.GuessGameTestHelper.givenValidGuessFlow
import presentation.utils.withGreenColor
import presentation.utils.withRedColor
import presentation.utils.withYellowColor


class GuessGameUIControllerTest {

    private lateinit var guessUseCase: GuessPreparationTimeUseCase
    private lateinit var console: ConsoleIO
    private lateinit var controller: GuessGameUIController

    @BeforeEach
    fun setup() {
        guessUseCase = mockk()
        console = mockk()
        controller = GuessGameUIController(guessUseCase, console)
    }

    @Test
    fun `should print meal name when execute is called`() {
        // Given
        givenValidGuessFlow(guessUseCase, console, mealName = "Pizza")

        // When
        controller.execute()

        // Then
        verify { console.println("\uD83C\uDFAF Guess the preparation time for: Pizza") }
    }

    @Test
    fun `should call checkGuess with entered number`() {
        // Given
        givenValidGuessFlow(guessUseCase, console, guess = 4, guessResult = Correct)

        // When
        controller.execute()

        // Then
        verify { guessUseCase.checkGuess(4) }
    }

    @Test
    fun `should print green message when guess is correct`() {
        // Given
        givenValidGuessFlow(guessUseCase, console, guess = 5, guessResult = Correct)

        // When
        controller.execute()

        // Then
        verify { console.println("Correct! You guessed it.".withGreenColor()) }
    }

    @Test
    fun `should print yellow message when guess is close`() {
        // Given
        givenMultipleGuesses(
            guessUseCase, console, guesses = listOf(5, 5), results = listOf(Close, Correct)
        )

        // When
        controller.execute()

        // Then
        verify { console.println("Wrong but you're very close!".withYellowColor()) }
    }

    @Test
    fun `should print red message when guess is too high`() {
        // Given
        givenMultipleGuesses(
            guessUseCase, console, guesses = listOf(7, 7), results = listOf(TooHigh, Correct)
        )

        // When
        controller.execute()

        // Then
        verify { console.println("Too high!".withRedColor()) }
    }

    @Test
    fun `should print red message when guess is too low`() {
        // Given
        givenMultipleGuesses(
            guessUseCase, console, guesses = listOf(3, 3), results = listOf(TooLow, Correct)
        )

        // When
        controller.execute()

        // Then
        verify { console.println("Too low!".withRedColor()) }
    }

    @Test
    fun `should retry game when guess is incorrect`() {
        // Given
        givenMultipleGuesses(
            guessUseCase,
            console,
            guesses = listOf(1, 1, 1),
            results = listOf(Close, TooHigh, Correct)
        )

        // When
        controller.execute()

        // Then
        verify(exactly = 3) { console.readInt(any()) }
    }

    @Test
    fun `should show attempts exceeded error message after 3 wrong guesses`() {
        // Given
        givenMultipleGuesses(
            guessUseCase,
            console,
            guesses = listOf(1, 1, 1),
            results = listOf(TooLow, TooHigh, Close)
        )

        // When
        controller.execute()

        // Then
        verify(exactly = 3) { console.readInt(any()) }
        verify { console.println("Game over! You've run out of attempts.".withRedColor()) }
    }
}