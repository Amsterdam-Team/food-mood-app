package presentation.uiController

import logic.usecase.GetItalianMealsForLargeGroupsUseCase
import logic.models.Meal
import presentation.utils.tryToExecute
import presentation.utils.withGreenColor

class ItalianMealUIController(
    private val getItalianMealsForLargeGroupsUseCase: GetItalianMealsForLargeGroupsUseCase
) : BaseUIController {
    override fun execute() {
        tryToExecute(
            action = getItalianMealsForLargeGroupsUseCase::getItalianMealsForLargeGroups,
            onSuccess = ::outputItalianMeals
        )
    }

    private fun outputItalianMeals(meals: List<Meal>) {
        println("Your Italian Meals for large groups\n".withGreenColor())
        meals.forEach { meal ->
            println("Meal Name: ${meal.name},   Description: ${meal.description}".withGreenColor())
            println("Ingredients:")
            meal.ingredients?.forEach {
                println(it.withGreenColor())
            }
        }
    }
}