package presentation.uiController

import logic.exception.FoodMoodException
import logic.usecase.GuessPreparationTimeUseCase
import presentation.utils.readValidInt
import presentation.utils.tryToExecute
import presentation.utils.withGreenColor
import presentation.utils.withRedColor
import presentation.utils.withYellowColor

class GuessGameUIController(
    private val guessUseCase: GuessPreparationTimeUseCase
) : BaseUIController {

    override fun execute() {
        tryToExecute(
            action = {
                val meal = guessUseCase.getGuessMealName()
                println("Guess the preparation time for: $meal")
                runGuessGame()
            },
            onSuccess = {
                println("Game finished!".withGreenColor())
            }
        )
    }

    private tailrec fun runGuessGame(remainingAttempts: Int = 3) {
        if (remainingAttempts == 0) {
            throw FoodMoodException.GameException.AttemptsExceeded
        }

        val guess = readValidInt("Your guess (Attempts left: $remainingAttempts):")
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

        runGuessGame(remainingAttempts - 1)
    }
}