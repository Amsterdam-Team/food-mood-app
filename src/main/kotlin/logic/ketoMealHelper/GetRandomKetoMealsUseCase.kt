package org.example.logic.ketoMealHelper

import logic.MealsRepository
import logic.exception.FoodMoodException
import logic.models.Meal

class GetRandomKetoMealsUseCase(
    private val mealsRepository: MealsRepository
) {

    fun getKetoMealRandomly(ketoMeals: List<Meal>): Meal {
        return ketoMeals.ifEmpty {
            throw FoodMoodException.EmptyDataException
        }.random()
    }


    fun getKetoMeals(): List<Meal> {
        return mealsRepository.getAllMeals().filter(::isKetoFriendlyMeal)
    }

    fun isKetoFriendlyMeal(meal: Meal): Boolean {
        return meal.nutrition?.totalFat?.let { it >= 20.0 } ?: false
                && meal.nutrition?.carbohydrates?.let { it in 0.0..10.0 } ?: false
                && meal.nutrition?.protein?.let { it in 20.0..30.0 } ?: false
    }

}