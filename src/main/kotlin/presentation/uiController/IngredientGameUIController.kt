package presentation.uiController

import presentation.models.IngredientGameResult
import logic.models.IngredientGameState
import logic.usecase.ingredient_game_usecases.StartIngredientGameUseCase
import logic.usecase.ingredient_game_usecases.SubmitAnswerUseCase

class IngredientGameUIController(
    private val startIngredientGameUseCase: StartIngredientGameUseCase ,
    private val submitAnswerUseCase: SubmitAnswerUseCase
) : BaseUIController {

    private val gameState = IngredientGameState()

    override fun execute() {
        showWelcomeScreen()


        while (!gameState.isFinished()) {
            when (val result = startIngredientGameUseCase.startGame(gameState)) {

                is IngredientGameResult.GameFinished -> {
                    showGameFinished(result.score)
                    break
                }

                is IngredientGameResult.GameInProgress -> {
                    showQuestion(result.mealName, result.ingredientOptions)

                    val inputIndex = readUserInput(result.ingredientOptions.size) - 1
                    val userAnswer = result.ingredientOptions[inputIndex]

                    val isCorrect = submitAnswerUseCase.submit(
                        userAnswer = userAnswer,
                        correctAnswer = result.correctIngredient,
                        gameState = gameState
                    )

                    showAnswerFeedback(isCorrect, result.correctIngredient, gameState.score)

                    if (!isCorrect) {
                        showGameOver(gameState.score)
                        break
                    }
                }

            }
        }

        println("\n🎮 Thank you for playing! See you next time.")
    }

    private fun showWelcomeScreen() {
        println("Welcome to the Ingredient Guessing Game!")
        println("Your goal is to guess one correct ingredient for each meal.")
        println("Get 15 correct answers to win. Let's start!\n")
    }

    private fun showQuestion(mealName: String, options: List<String>) {
        println("\n🍽️ Meal: $mealName")
        println("Which of the following is one of its ingredients?")
        options.forEachIndexed { index, option ->
            println("${index + 1}. $option")
        }
    }

    private fun readUserInput(maxOption: Int): Int {
        var input: Int?
        do {
            print("👉 Enter your choice (1-$maxOption): ")
            input = readLine()?.toIntOrNull()
            if (input !in 1..maxOption) {
                println("🚫 Invalid input. Please enter a number from 1 to $maxOption.")
            }
        } while (input == null || input !in 1..maxOption)
        return input
    }

    private fun showAnswerFeedback(isCorrect: Boolean, correctAnswer: String, score: Int) {
        if (isCorrect) {
            println("✅ Correct! Current Score: $score")
        } else {
            println("❌ Wrong! The correct answer was: $correctAnswer")
        }
    }

    private fun showGameFinished(score: Int) {
        println("\n🎉 Congratulations! You completed the game!")
        println("🏆 Final Score: $score")
    }

    private fun showGameOver(score: Int) {
        println("\n💥 Game Over!")
        println("🎯 Final Score: $score")
    }
}





