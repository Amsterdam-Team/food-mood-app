package logic.usecase

import data.MealSuggestionDataStore
import logic.MealsRepository
import logic.exception.FoodMoodException
import logic.models.Meal
import presentation.utils.getRandomElementOrNull

class SuggestAMealByCaloriesUseCase(
    private val mealsRepository: MealsRepository, private val mealSuggestionDataStore: MealSuggestionDataStore
) {


    fun filteredMealByCalories(): List<Meal> {
        return mealsRepository.getAllMeals().filter {
            (it.nutrition?.calories ?: DEFAULT_CALORIES_VALUE) > INPUT_CALORIES
        }
    }

    fun getMealRandomly(): Meal {
        val validMeals = filteredMealByCalories()

        if (validMeals.isEmpty()) throw FoodMoodException.Validation.EmptyDataException

        if (validMeals.size == mealSuggestionDataStore.checkTotalSeenSuggestedMeals()) {
            throw FoodMoodException.Validation.NoMoreSuggestion
        }

        val randomMeal = validMeals.getRandomElementOrNull() ?: throw FoodMoodException.Validation.EmptyDataException

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

