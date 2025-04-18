package data

import logic.models.Meal

class MealSuggestionDataStore {
    private val list: MutableList<Meal> = mutableListOf()

    fun addSeenSuggestedMeal(meal: Meal) = list.add(meal)

    fun checkSeenSuggestedMeal(meal: Meal) = list.contains(meal)

    fun checkTotalSeenSuggestedMeals() = list.size
}