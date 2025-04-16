package presentation

import logic.GetIraqiMealsUseCase
import logic.models.Meal
import presentation.utils.tryToExecute
import presentation.utils.withGreenColor

class IraqiMealUI (
    private val getIraqiMealsUseCase: GetIraqiMealsUseCase
){
    fun start(){
        tryToExecute(action = getIraqiMealsUseCase::getOnlyIraqiMeals, onSuccess = ::outputIraqiMeals)
    }

    private fun outputIraqiMeals(meals: List<Meal>){
        println("Your Iraqi Meals\n".withGreenColor())
        meals.forEach { meal ->
            println("Meal Name: ${meal.name},   Description: ${meal.description}".withGreenColor())
            println("Ingredients: ")
            meal.ingredients?.forEach {
                println(it.withGreenColor())
            }
        }
    }
}