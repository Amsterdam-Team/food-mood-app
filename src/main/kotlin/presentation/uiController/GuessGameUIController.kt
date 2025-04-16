package presentation.uiController

import logic.exception.FoodMoodException
import logic.usecase.GuessPreparationTimeUseCase
import presentation.utils.readValidInt
import presentation.utils.tryToExecute
import presentation.utils.withGreenColor
import presentation.utils.withRedColor
import presentation.utils.withYellowColor
import kotlin.math.abs

class GuessGameUIController(
    private val guessPreparationTimeUseCase: GuessPreparationTimeUseCase
) : BaseUIController {

    override fun execute() {
        tryToExecute(
            action = {
                val meal = guessPreparationTimeUseCase.getRandomMeal()
                val actualTime = meal.preparationTime
                    ?: throw FoodMoodException.Validation.MissingPreparationTime

                println("Guess the preparation time for: ${meal.name}")
                runGuessGame(actualTime)
            },
            onSuccess = {
                println("Game finished!".withGreenColor())
            }
        )
    }

    private tailrec fun runGuessGame(
        actualTime: Int,
        remainingAttempts: Int = 3
    ) {
        if (remainingAttempts == 0) {
            throw FoodMoodException.GameException.AttemptsExceeded
        }

        val guess = readValidInt("Your guess (Attempts left: $remainingAttempts):")

        val result = guessPreparationTimeUseCase.checkGuess(actualTime, guess)
        val difference = abs(actualTime - guess)

        if (difference in 1..2) println("You’re very close!".withYellowColor())

        when (result) {
            is GuessPreparationTimeUseCase.ComparisonResult.Correct -> {
                println("Correct! It takes $actualTime minutes.".withGreenColor())
                return
            }

            is GuessPreparationTimeUseCase.ComparisonResult.TooHigh -> println("Too high!".withRedColor())
            is GuessPreparationTimeUseCase.ComparisonResult.TooLow -> println("Too low!".withRedColor())
        }

        runGuessGame(actualTime, remainingAttempts - 1)
    }
}