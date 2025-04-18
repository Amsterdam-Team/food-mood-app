package logic.usecase

import data.MealSuggestionDataStore
import logic.MealsRepository
import logic.exception.FoodMoodException
import logic.models.Meal
import org.example.presentation.utils.getRandomElementOrNull

class SuggestAMealByCaloriesUseCase(
    private val mealsRepository: MealsRepository,
    private val mealSuggestionDataStore: MealSuggestionDataStore
) {

    private val filteredMealByCalories: List<Meal> by lazy {
        mealsRepository.getAllMeals().filter {
            (it.nutrition?.calories ?: DEFAULT_CALORIES_VALUE) > INPUT_CALORIES
        }
    }

    fun getMealRandomly(): Meal {
        val validMeals = filteredMealByCalories.ifEmpty { throw FoodMoodException.Validation.EmptyDataException }
        val randomMeal = validMeals.getRandomElementOrNull() ?: throw FoodMoodException.Validation.EmptyDataException
        if (validMeals.size == mealSuggestionDataStore.checkTotalSeenSuggestedMeals()) {
            throw FoodMoodException.Validation.NoMoreSuggestion
        }
        if (mealSuggestionDataStore.checkSeenSuggestedMeal(randomMeal)) {
            getMealRandomly()
        } else {
            mealSuggestionDataStore.addSeenSuggestedMeal(randomMeal)
        }
        return randomMeal
    }

    private companion object {
        const val INPUT_CALORIES = 700.0
        const val DEFAULT_CALORIES_VALUE = 0.0
    }
}

