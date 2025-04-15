package org.example.presentation

import org.example.logic.SearchByIngredientsUseCase

class ILovePotatoUI(private val searchByIngredientsUseCase: SearchByIngredientsUseCase) {
    fun start() {
        println("Hi, potato lover!")

        val potatoMeals = searchByIngredientsUseCase.getMealByIngredient("potatoes")

        if (potatoMeals.isEmpty()) {
            println("No meals found that have potatoes in their ingredients.")
            return
        }

        println("Here are some meals that contain potatoes:")

        val displayedMeals = potatoMeals.shuffled().take(10)

        displayedMeals.forEachIndexed { index, meal ->
            println("${index + 1}. Meal: ${meal.name}")
            println("   Ingredients: ${meal.ingredients}")
        }
    }
}