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
            action = { ::onStartGuessing },
            onSuccess = { ::onSuccessfulGuess }
        )
    }

    private fun onStartGuessing() {
        val mealName = guessUseCase.getCurrentMeal()
        println("🎯 Guess the preparation time for: $mealName")
        runGuessGame()
    }

    private fun onSuccessfulGuess() {
        println("Correct! You guessed it.".withGreenColor())
    }

    private tailrec fun runGuessGame(remainingAttempts: Int = 3) {
        if (remainingAttempts == 0) {
            throw FoodMoodException.GameException.AttemptsExceeded
        }

        val guess = readValidInt("Your guess (Attempts left: $remainingAttempts):")
        val result = guessUseCase.checkGuess(guess)

        when (result) {
            is GuessPreparationTimeUseCase.ComparisonResult.Correct -> return

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

        runGuessGame(remainingAttempts - 1)
    }
}