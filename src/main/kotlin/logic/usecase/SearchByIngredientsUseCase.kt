package logic.usecase

import logic.MealsRepository
import logic.exception.FoodMoodException
import logic.models.Meal

class SearchByIngredientsUseCase (private val mealsRepository: MealsRepository) {
    fun getMealByIngredient(ingredient:String): List<Meal>{
        return mealsRepository.getAllMeals().filter {
            it.ingredients!=null && it.ingredients.contains(ingredient)
        }
    }
}