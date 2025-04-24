package presentation.uiController.helpers

import io.mockk.every
import io.mockk.just
import io.mockk.runs
import logic.usecase.GuessPreparationTimeUseCase
import logic.usecase.GuessPreparationTimeUseCase.ComparisonResult
import presentation.console.ConsoleIO

object GuessGameTestHelper {

    fun givenValidGuessFlow(
        guessUseCase: GuessPreparationTimeUseCase,
        console: ConsoleIO,
        mealName: String = "Pizza",
        guess: Int = 5,
        guessResult: ComparisonResult = ComparisonResult.Correct
    ) {
        every { guessUseCase.getCurrentMeal() } returns mealName
        every { console.readInt(any()) } returns guess
        every { guessUseCase.checkGuess(guess) } returns guessResult
        every { console.println(any()) } just runs
    }

    fun givenMultipleGuesses(
        guessUseCase: GuessPreparationTimeUseCase,
        console: ConsoleIO,
        mealName: String = "Pasta",
        guesses: List<Int>,
        results: List<ComparisonResult>
    ) {
        every { guessUseCase.getCurrentMeal() } returns mealName
        every { console.readInt(any()) } returnsMany guesses
        every { guessUseCase.checkGuess(any()) } returnsMany results
        every { console.println(any()) } just runs
    }
}
