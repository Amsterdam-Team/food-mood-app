package logic.usecase

import logic.MealsRepository
import logic.models.Meal

class SuggestTop10EasyMealsUseCase(
    private val mealsRepository: MealsRepository
) {
    fun suggestEasyMeals(): List<Meal> {
        return mealsRepository.getAllMeals().filter(::onSuggestEasyMeals).shuffled().take(10)
    }
    private fun onSuggestEasyMeals(meal: Meal): Boolean {
        return (meal.preparationTime ?: Int.MAX_VALUE) <= 30 &&
                (meal.numberOfIngredients ?: Int.MAX_VALUE) <= 5 &&
                (meal.numberOfSteps ?: Int.MAX_VALUE) <= 6
    }
}