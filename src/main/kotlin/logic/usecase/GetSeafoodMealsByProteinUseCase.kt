package org.example.logic.usecase

import org.example.logic.EmptyDataException
import org.example.logic.MealsRepository
import org.example.models.Meal
import org.example.models.isSeafood

class GetSeafoodMealsByProteinUseCase(
    private val mealsRepository: MealsRepository
) {

    fun getSeafoodMealsByProteinUseCase(): List<String> {
        val allMeals = mealsRepository.getAllMeals()
        val validMeals = getValidMeals(allMeals).ifEmpty { throw EmptyDataException() }
        val seafoodMeals = filterSeafoodMeals(validMeals).ifEmpty { throw EmptyDataException() }
        val sortedSeafoodMealsByProtein = sortMealsByProtein(seafoodMeals).ifEmpty { throw EmptyDataException() }

        return sortedSeafoodMealsByProtein.mapIndexed { index, meal ->
            "Rand: ${index + 1} Meal Name: ${meal.name} Protein Amount: ${meal.nutrition?.protein}"
        }

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