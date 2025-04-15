package org.example.logic

import org.example.models.Meal

class GetFastHealthyMealsUseCase {

    fun getFastHealthMeals(meals: List<Meal>): List<Meal> {
        val validMeals = getValidMeals(meals)
        if (validMeals.isEmpty()) throw EmptyDataException()
        val healthyMeals = filterHealthyMeals(validMeals)
        if (healthyMeals.isEmpty()) throw EmptyDataException()

        return healthyMeals
    }

    private fun getValidMeals(meals: List<Meal>): List<Meal> {
        return meals.filter { it.nutrition != null && it.preparationTime != null }
    }

    private fun filterHealthyMeals(meals: List<Meal>): List<Meal> {
        val totalFatAvg = meals.mapNotNull { it.nutrition?.totalFat }.average()
        val satFatAvg = meals.mapNotNull { it.nutrition?.saturatedFat }.average()
        val carbsAvg = meals.mapNotNull { it.nutrition?.carbohydrates }.average()

        val healthyMeals = meals.filter { meal ->
            val nutrition = meal.nutrition!!
            meal.preparationTime!! <= PREPARATION_TIME_TARGET &&
                    isBelowAverage(nutrition.totalFat, totalFatAvg) &&
                    isBelowAverage(nutrition.saturatedFat, satFatAvg) &&
                    isBelowAverage(nutrition.carbohydrates, carbsAvg)
        }

        return healthyMeals
    }

    private fun isBelowAverage(selector: Double?, average: Double, default: Double = 0.0) =
        (selector ?: default) < average


    private companion object {
        const val PREPARATION_TIME_TARGET = 15

    }
}