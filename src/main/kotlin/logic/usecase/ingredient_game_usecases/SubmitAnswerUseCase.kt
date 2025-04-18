package logic.usecase.ingredient_game_usecases

import logic.models.IngredientGameState
//
class SubmitAnswerUseCase {
    fun submit(userAnswer: String, correctAnswer: String, gameState: IngredientGameState): Boolean {
        val isCorrect = userAnswer.equals(correctAnswer, ignoreCase = true)
        if (isCorrect) gameState.increaseScore()
        return isCorrect
    }
}
