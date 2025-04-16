package logic.usecase

import logic.MealsRepository
import logic.exception.FoodMoodException.Validation.*
import logic.models.Meal
import org.example.presentation.utils.getRandomElementOrNull

class GuessPreparationTimeUseCase(private val repository: MealsRepository) {

    fun getRandomMeal(): Meal {
        return repository.getAllMeals()
            .filter { it.name != null && it.preparationTime != null }
            .getRandomElementOrNull() ?: throw EmptyDataException
    }

    fun checkGuess(actual: Int, guess: Int): ComparisonResult {
        return when {
            guess == actual -> ComparisonResult.Correct
            guess > actual -> ComparisonResult.TooHigh
            else -> ComparisonResult.TooLow
        }
    }

    sealed class ComparisonResult {
        data object Correct : ComparisonResult()
        data object TooHigh : ComparisonResult()
        data object TooLow : ComparisonResult()
    }
}