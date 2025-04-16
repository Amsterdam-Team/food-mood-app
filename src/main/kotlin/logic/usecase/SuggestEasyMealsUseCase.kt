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
      return  input.preparationTime!! <= 30 && input.numberOfIngredients !!<= 5 && input.numberOfSteps!! <= 6
    }
}
