package data

import logic.models.Meal

class KetoMealsDataStore {
    private val seenKetoMeals: MutableList<Meal> = mutableListOf()

    fun addSeenKetoMeal(meal: Meal) = seenKetoMeals.add(meal)

    fun checkSeenKetoMeal(meal: Meal) = seenKetoMeals.contains(meal)

    fun checkTotalSeenKetoMeals() = seenKetoMeals.size
}