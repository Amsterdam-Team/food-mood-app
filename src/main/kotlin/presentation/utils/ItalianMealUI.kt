package presentation.utils

import logic.GetItalianMealsForLargeGroupsUseCase
import logic.models.Meal

class ItalianMealUI(
    private val getItalianMealsForLargeGroupsUseCase: GetItalianMealsForLargeGroupsUseCase
) {
    fun start(){
        tryToExecute(action = getItalianMealsForLargeGroupsUseCase::getItalianMealsForLargeGroups, onSuccess = ::outputItalianMeals)
    }

    private fun outputItalianMeals(meals: List<Meal>){
        println("Your Italian Meals for large groups\n".withGreenColor())
        meals.forEach { meal ->
            println("Meal Name: ${meal.name},   Description: ${meal.description}".withGreenColor())
            println("Ingredients: ")
            meal.ingredients?.forEach {
                println(it.withGreenColor())
            }
        }
    }
}