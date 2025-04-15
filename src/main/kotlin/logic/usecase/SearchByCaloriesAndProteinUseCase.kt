package org.example.logic.usecase

import org.example.logic.MealsRepository
import org.example.models.Meal
import kotlin.math.ceil


class SearchByCaloriesAndProteinUseCase(private val mealsRepository: MealsRepository) {
    fun getMealByCaloriesAndProtein(calories: Int, protein: Int, approximationRatio: Float = 0.1f): List<Meal> {

        return filterMealsByApproximateCaloriesAndProtein(
            meals = mealsRepository.getAllMeals(),
            calories = calories,
            protein = protein,
            approximationRatio = approximationRatio
        )


    }

    private fun filterMealsByApproximateCaloriesAndProtein(
        meals: List<Meal>,
        calories: Int,
        protein: Int,
        approximationRatio: Float
    ): List<Meal> {
        val caloriesRange = getApproximateRange(calories, approximationRatio)
        val proteinRange = getApproximateRange(protein, approximationRatio)

        return meals.filter { meal ->
            val nutrition = meal.nutrition
            nutrition?.calories != null &&
                    nutrition.protein != null &&
                    nutrition.calories.toInt() in caloriesRange &&
                    nutrition.protein.toInt() in proteinRange
        }
    }


    private fun getApproximateRange(number: Int, ratio: Float): IntRange {
        val percentage = ceil(number * ratio).toInt()
        return (number - percentage)..(number + percentage)
    }

}