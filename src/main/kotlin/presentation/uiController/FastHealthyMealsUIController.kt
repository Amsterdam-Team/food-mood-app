package presentation.uiController

import logic.GetFastHealthyMealsUseCase
import logic.models.Meal
import presentation.utils.tryToExecute
import presentation.utils.withGreenColor

class FastHealthyMealsUIController(private val fastHealthyMealsUseCase: GetFastHealthyMealsUseCase) : BaseUIController {
    override fun execute() {
        tryToExecute(
            action = fastHealthyMealsUseCase::getFastHealthMeals,
            onSuccess = ::onGetFastHealthyMealsSuccess
        )
    }

    private fun onGetFastHealthyMealsSuccess(meals: List<Meal>) {
        meals.forEach { meal ->
            println(formatMeal(meal).withGreenColor())
        }
    }

    private fun formatMeal(meal: Meal) = meal.toString().trimIndent()
}