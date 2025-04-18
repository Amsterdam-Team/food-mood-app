package presentation.uiController

import logic.usecase.GetIraqiMealsUseCase
import logic.models.Meal
import presentation.utils.tryToExecute
import presentation.utils.withGreenColor

class IraqiMealUIController(
    private val getIraqiMealsUseCase: GetIraqiMealsUseCase
) : BaseUIController {
    override fun execute() {
        tryToExecute(
            action = getIraqiMealsUseCase::getOnlyIraqiMeals,
            onSuccess = ::outputIraqiMeals
        )
    }

    private fun outputIraqiMeals(meals: List<Meal>) {
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