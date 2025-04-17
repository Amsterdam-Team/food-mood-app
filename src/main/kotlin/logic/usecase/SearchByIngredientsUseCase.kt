package logic.usecase

import logic.MealsRepository
import logic.exception.FoodMoodException.Validation.EmptyDataException
import logic.models.Meal

class SearchByIngredientsUseCase (private val mealsRepository: MealsRepository) {
    fun getMealByIngredient(ingredient:String): List<Meal>{
        return mealsRepository.getAllMeals().filter {
            it.ingredients!=null && it.ingredients.contains(ingredient)
        }.also {
           if( it.isEmpty()) throw EmptyDataException
        }
    }
}