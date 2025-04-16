package logic.usecase

import logic.MealsRepository
import logic.exception.FoodMoodException.Validation.EmptyDataException
import logic.exception.FoodMoodException.Validation.MissingPreparationTime
import logic.models.Meal
import presentation.utils.getRandomElementOrNull
import kotlin.math.abs

class GuessPreparationTimeUseCase(private val repository: MealsRepository) {

    private val mealToGuess: Meal by lazy {
        repository.getAllMeals()
            .filter { it.name != null && it.preparationTime != null }
            .getRandomElementOrNull() ?: throw EmptyDataException
    }

    fun getGuessMealName(): String {
        return mealToGuess.name ?: throw EmptyDataException
    }

    fun checkGuess(guess: Int): ComparisonResult {
        val actual = mealToGuess.preparationTime ?: throw MissingPreparationTime
        val difference = abs(guess - actual)

        return when {
            guess == actual -> ComparisonResult.Correct
            difference in 1..2 -> ComparisonResult.Close
            guess > actual -> ComparisonResult.TooHigh
            else -> ComparisonResult.TooLow
        }
    }

    sealed class ComparisonResult {
        data object Correct : ComparisonResult()
        data object TooHigh : ComparisonResult()
        data object TooLow : ComparisonResult()
        data object Close : ComparisonResult()
    }
}