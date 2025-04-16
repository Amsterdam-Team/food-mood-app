package logic.usecase

import logic.MealsRepository
import logic.models.Meal
import logic.models.isSeafood
import org.example.logic.EmptyDataException


class GetSeafoodMealsByProteinUseCase(
    private val mealsRepository: MealsRepository
) {

    fun getSeafoodMealsByProteinUseCase(): List<Meal> {
        val allMeals = mealsRepository.getAllMeals()
        val validMeals = getValidMeals(allMeals).ifEmpty { throw EmptyDataException() }
        val seafoodMeals = filterSeafoodMeals(validMeals).ifEmpty { throw EmptyDataException() }

        return sortMealsByProtein(seafoodMeals).ifEmpty { throw EmptyDataException() }
    }


    private fun getValidMeals(meals: List<Meal>): List<Meal> {
        return meals.filter { it.nutrition?.protein != null && it.name != null }
    }

    private fun filterSeafoodMeals(meals: List<Meal>): List<Meal> {
        return meals.filter { it.isSeafood() }
    }

    private fun sortMealsByProtein(meals: List<Meal>): List<Meal> {
        return meals.sortedByDescending { it.nutrition?.protein }
    }
}