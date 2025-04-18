package logic.usecase.ingredient_game_usecases

import logic.MealsRepository
import logic.exception.FoodMoodException
import presentation.models.IngredientGameResult
import logic.models.IngredientGameState
import logic.models.Meal
import presentation.utils.getRandomElementOrNull
import presentation.utils.getRandomElements
//
class StartIngredientGameUseCase(private val mealsRepository: MealsRepository) {

    fun startGame(gameState: IngredientGameState): IngredientGameResult {
        if (gameState.isFinished()) {
            return IngredientGameResult.GameFinished(
                score = gameState.score,
                correctAnswers = gameState.correctAnswers
            )
        }

        val availableMeal = getAvailableMeal(gameState)
        val correctIngredient = getCorrectIngredient(availableMeal)
        val distractors = getDistractors(availableMeal, gameState, correctIngredient)

        if (distractors.size < 2) throw FoodMoodException.GameException.IngredientOptionsNotEnough

        val options = (distractors + correctIngredient).shuffled()

        return IngredientGameResult.GameInProgress(
            mealName = availableMeal.name!!,
            ingredientOptions = options,
            correctIngredient = correctIngredient,
            score = gameState.score,
            correctAnswers = gameState.correctAnswers
        )
    }

    private fun getAvailableMeal(gameState: IngredientGameState): Meal {
        val meals = mealsRepository.getAllMeals()
        return meals
            .filter { it.id != null && it.id !in gameState.usedMealIds && !it.ingredients.isNullOrEmpty() }
            .getRandomElementOrNull()
            ?: throw FoodMoodException.GameException.NoMealsAvailable
    }

    private fun getCorrectIngredient(availableMeal: Meal): String {
        return availableMeal.ingredients!!.getRandomElementOrNull()
            ?: throw FoodMoodException.GameException.MealDataCorrupted
    }

    private fun getDistractors(availableMeal: Meal, gameState: IngredientGameState, correctIngredient: String): List<String> {
        val meals = mealsRepository.getAllMeals()
        return meals
            .filter { it.id != availableMeal.id }
            .flatMap { it.ingredients ?: emptyList() }
            .distinct()
            .filterNot { it.equals(correctIngredient, ignoreCase = true) }
            .getRandomElements(2)
    }
}