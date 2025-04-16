package presentation.uiController

import logic.models.Meal
import logic.usecase.SuggestTop10EasyMealsUseCase
import presentation.utils.tryToExecute
import presentation.utils.withGreenColor

class SuggestTop10EasyMealsUI(
    private val suggestTop10EasyMealsUseCase: SuggestTop10EasyMealsUseCase
) : BaseUIController {
    override fun execute() {
        tryToExecute(
            action = suggestTop10EasyMealsUseCase::suggestEasyMeals,
            onSuccess = ::outputsuggestTop10EasyMeals
        )
    }

    private fun outputsuggestTop10EasyMeals(meals: List<Meal>) {
        println("Suggest Easy Meal ".withGreenColor())
        meals.forEachIndexed { index, meal ->
            println("${index + 1} - Meal Name: ${meal.name},   ".withGreenColor())
        }
    }

}
