package presentation.uiController

import logic.exception.FoodMoodException
import logic.usecase.GuessPreparationTimeUseCase
import presentation.console.ConsoleIO
import presentation.utils.tryToExecute
import presentation.utils.withGreenColor
import presentation.utils.withRedColor
import presentation.utils.withYellowColor

class GuessGameUIController(
    private val guessUseCase: GuessPreparationTimeUseCase,
    private val console: ConsoleIO
) : BaseUIController {

    override fun execute() {
        tryToExecute(
            action = ::onStartGuessing,
            onSuccess = { onRunGuessGameSuccess() }

        )
    }

    private fun onStartGuessing() {
        val mealName = guessUseCase.getCurrentMeal()
        console.println("🎯 Guess the preparation time for: $mealName")
    }

    private fun readGuessInput(remainingAttempts: Int): Int {
        return console.readInt("Your guess (Attempts left: $remainingAttempts):")
    }

    private tailrec fun onRunGuessGameSuccess(remainingAttempts: Int = 3) {
        if (remainingAttempts == 0) {
            throw FoodMoodException.GameException.AttemptsExceeded
        }

        val guess = readGuessInput(remainingAttempts)
        val result = guessUseCase.checkGuess(guess)

        when (result) {
            is GuessPreparationTimeUseCase.ComparisonResult.Correct -> {
                console.println("Correct! You guessed it.".withGreenColor())
                return
            }

            is GuessPreparationTimeUseCase.ComparisonResult.Close -> {
                console.println("Wrong but you're very close!".withYellowColor())
            }

            is GuessPreparationTimeUseCase.ComparisonResult.TooHigh -> {
                console.println("Too high!".withRedColor())
            }

            is GuessPreparationTimeUseCase.ComparisonResult.TooLow -> {
                console.println("Too low!".withRedColor())
            }
        }

        onRunGuessGameSuccess(remainingAttempts - 1)
    }
}