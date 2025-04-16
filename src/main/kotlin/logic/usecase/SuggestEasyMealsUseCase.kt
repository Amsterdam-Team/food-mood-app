package logic.usecase

import logic.MealsRepository
import logic.models.Meal

class SuggestEasyMealsUseCase(
    private val mealsRepository: MealsRepository

) {
    fun suggestEasyMeals(): List<Meal> {
        return mealsRepository.getAllMeals().filter(::onSuggestEasyMeals).shuffled().take(10)
    }
    private fun onSuggestEasyMeals(input:Meal):Boolean{
        return (input.preparationTime ?: Int.MAX_VALUE) <= 30 &&
                (input.numberOfIngredients ?: Int.MAX_VALUE) <= 5 &&
                (input.numberOfSteps ?: Int.MAX_VALUE) <= 6
    }
}
