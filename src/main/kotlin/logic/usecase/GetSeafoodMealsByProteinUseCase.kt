package logic.usecase

import logic.MealsRepository
import logic.exception.FoodMoodException
import logic.models.Meal


class GetSeafoodMealsByProteinUseCase(
    private val mealsRepository: MealsRepository
) {

    fun getSeafoodMealsByProteinUseCase(): List<Meal> {
        val allMeals = mealsRepository.getAllMeals()
        val validMeals = getValidMeals(allMeals).ifEmpty { throw FoodMoodException.Validation.EmptyDataException }
        val seafoodMeals =
            filterSeafoodMeals(validMeals).ifEmpty { throw FoodMoodException.Validation.EmptyDataException }

        return sortMealsByProtein(seafoodMeals).ifEmpty { throw FoodMoodException.Validation.EmptyDataException }
    }


    private fun getValidMeals(meals: List<Meal>): List<Meal> {
        return meals.filter { it.nutrition?.protein != null && it.name != null }
    }

    private fun filterSeafoodMeals(meals: List<Meal>): List<Meal> {
        return meals.filter { it.tags?.joinToString(" ")?.lowercase().orEmpty().contains(FISH_TAG) }
    }

    private fun sortMealsByProtein(meals: List<Meal>): List<Meal> {
        return meals.sortedByDescending { it.nutrition?.protein }
    }

    private companion object {
        const val FISH_TAG = "seafood"
    }
}