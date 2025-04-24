package logic.usecase

import logic.MealsRepository
import logic.exception.FoodMoodException
import logic.models.Meal

class SuggestTop10EasyMealsUseCase(
    private val mealsRepository: MealsRepository
) {
    fun suggestEasyMeals(): List<Meal> {
        val allMeals = mealsRepository.getAllMeals()
        val validMeals = allMeals.ifEmpty { throw FoodMoodException.Validation.EmptyDataException }

        return validMeals.filter(::onSuggestEasyMeals).shuffled().take(10)
    }
    private fun onSuggestEasyMeals(meal: Meal): Boolean {

        return  meal.preparationTime != null &&
                meal.numberOfIngredients != null &&
                meal.numberOfSteps != null &&
                meal.preparationTime <= 30 &&
                meal.numberOfIngredients <= 5 &&
                meal.numberOfSteps <= 6
    }
}