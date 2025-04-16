import logic.usecase.IngredientGameResult
import logic.usecase.PlayIngredientGameUseCase
import presentation.uiController.BaseUIController
import presentation.utils.tryToExecute

class PlayIngredientGameUI(private val playIngredientGameUseCase: PlayIngredientGameUseCase): BaseUIController {

    override fun execute() {
        showWelcomeScreen()
        tryToExecute(
            action = ::playIngredientGameAction,
            onSuccess = {
                println("\nThe game ended successfully!")
                println("\nThank you for playing! See you next time")
            }
        )

    }

    private fun showWelcomeScreen() {
        println("Welcome to the Ingredient Guessing Game!")
        println("Your goal is to guess one correct ingredient for each meal.")
        println("Get 15 correct answers to win. Let's start!\n")
    }

    private fun readUserInput(): Int {
        var input: Int?

        do {
            print("👉 Enter your choice (1-3): ")
            input = readLine()?.toIntOrNull()
            if (input == null || input !in 1..3) {
                println("🚫 Invalid input. Please enter a number from 1 to 3.")
            }
        } while (input == null || input !in 1..3)

        return input
    }


//    private fun onGameFinishedSuccessfully(/*result: IngredientGameResult*/) {
//        println("\nThe game ended successfully! Your Score: ${result.score}")
//        println("\nThank you for playing! See you next time")
//    }

    private fun showQuestion(result: IngredientGameResult) {
        println("\n🍽️ Meal: ${result.mealName}")
        println("Which of the following is one of its ingredients?")
        result.ingredientOptions.forEachIndexed { index, option ->
            println("${index + 1}. $option")
        }
    }


    private fun playIngredientGameAction(){
        var isFinished = false

        while (!isFinished) {
            val gameResult = playIngredientGameUseCase.startGame()

            isFinished = gameResult.isFinished
            if (isFinished) {
                println("\nYou win! Final Score: ${gameResult.score}")
                break
            }

            showQuestion(gameResult)

            val input = readUserInput()
            val userAnswer = gameResult.ingredientOptions[input - 1]
            val isCorrect = playIngredientGameUseCase.submitAnswer(userAnswer)

            if (isCorrect) {
                println("Correct! Score: ${gameResult.score + 1000}")
            } else {
                println("Wrong! The correct answer was: ${gameResult.correctIngredient}")
                println("\nGame Over! Final Score: ${gameResult.score}")
                isFinished = true
            }
        }
    }
}



