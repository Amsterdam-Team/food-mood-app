package presentation.uiController

import logic.exception.FoodMoodException
import logic.usecase.GuessPreparationTimeUseCase
import presentation.utils.readValidInt
import presentation.utils.tryToExecute
import presentation.utils.withGreenColor
import presentation.utils.withRedColor
import presentation.utils.withYellowColor

class GuessGameUIController(private val guessUseCase: GuessPreparationTimeUseCase) :
    BaseUIController {

    override fun execute() {
        tryToExecute(
            action = ::onStartGuessing,
            onSuccess = { onRunGuessGameSuccess() }

        )
    }

    private fun onStartGuessing() {
        val mealName = guessUseCase.getCurrentMeal()
        println("🎯 Guess the preparation time for: $mealName")
    }

    private fun readGuessInput(remainingAttempts: Int): Int {
        return readValidInt("Your guess (Attempts left: $remainingAttempts):")
    }

    private tailrec fun onRunGuessGameSuccess(remainingAttempts: Int = 3) {
        if (remainingAttempts == 0) {
            throw FoodMoodException.GameException.AttemptsExceeded
        }

        val guess = readGuessInput(remainingAttempts)
        val result = guessUseCase.checkGuess(guess)

        when (result) {
            is GuessPreparationTimeUseCase.ComparisonResult.Correct -> {
                println("Correct! You guessed it.".withGreenColor())
                return
            }

            is GuessPreparationTimeUseCase.ComparisonResult.Close -> {
                println("Wrong but you're very close!".withYellowColor())
            }

            is GuessPreparationTimeUseCase.ComparisonResult.TooHigh -> {
                println("Too high!".withRedColor())
            }

            is GuessPreparationTimeUseCase.ComparisonResult.TooLow -> {
                println("Too low!".withRedColor())
            }
        }

        onRunGuessGameSuccess(remainingAttempts - 1)
    }
}