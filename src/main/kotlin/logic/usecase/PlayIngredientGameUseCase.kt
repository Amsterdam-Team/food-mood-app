package logic.usecase

import logic.MealsRepository
import logic.exception.FoodMoodException
import logic.models.Meal
import org.example.presentation.utils.getRandomElementOrNull
import org.example.presentation.utils.getRandomElements

data class IngredientGameResult(
    val mealName: String,
    val ingredientOptions: List<String>,
    val correctIngredient: String,
    val score: Int,
    val correctAnswers: Int,
    val isFinished: Boolean
)

class PlayIngredientGameUseCase(private val mealsRepository: MealsRepository) {

    private var score = 0
    private var correctAnswers = 0
    private val usedMealIds = mutableSetOf<String>()
    private lateinit var currentMeal: Meal
    private lateinit var correctIngredient: String

    fun startGame(): IngredientGameResult {
        val meals = mealsRepository.getAllMeals()
        if (meals.isEmpty()) throw FoodMoodException.GameException.NoMealsAvailable

        if (correctAnswers >= 15) {
            return IngredientGameResult(
                mealName = "",
                ingredientOptions = emptyList(),
                correctIngredient = "",
                score = score,
                correctAnswers = correctAnswers,
                isFinished = true
            )
        }

        currentMeal = selectValidMeal(meals)
        correctIngredient = selectCorrectIngredient(currentMeal)
        val options = generateIngredientOptions(meals, currentMeal, correctIngredient)

        return IngredientGameResult(
            mealName = currentMeal.name ?: throw FoodMoodException.GameException.MealDataCorrupted,
            ingredientOptions = options,
            correctIngredient = correctIngredient,
            score = score,
            correctAnswers = correctAnswers,
            isFinished = false
        )
    }

    fun submitAnswer(answer: String): Boolean {
        val isCorrect = answer.equals(correctIngredient, ignoreCase = true)
        if (isCorrect) {
            score += 1000
            correctAnswers++
        }
        return isCorrect
    }

    private fun selectValidMeal(meals: List<Meal>): Meal {
        val meal = meals
            .filter { it.id !in usedMealIds && !it.ingredients.isNullOrEmpty() }
            .getRandomElementOrNull() ?: throw FoodMoodException.GameException.NoMealsAvailable

        val id = meal.id ?: throw FoodMoodException.GameException.MealDataCorrupted
        usedMealIds.add(id)
        return meal
    }

    private fun selectCorrectIngredient(meal: Meal): String {
        return meal.ingredients?.getRandomElementOrNull()
            ?: throw FoodMoodException.GameException.MealDataCorrupted
    }

    private fun generateIngredientOptions(meals: List<Meal>, currentMeal: Meal, correctIngredient: String): List<String> {
        val distractors = meals
            .filter { it.id != currentMeal.id }
            .flatMap { it.ingredients ?: emptyList() }
            .distinct()
            .filterNot { it.equals(correctIngredient, ignoreCase = true) }
            .getRandomElements(2)

        if (distractors.size < 2) throw FoodMoodException.GameException.IngredientOptionsNotEnough

        return (listOf(correctIngredient) + distractors).shuffled()
    }
}

