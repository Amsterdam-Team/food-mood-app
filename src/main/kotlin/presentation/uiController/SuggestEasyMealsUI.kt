package presentation.uiController

import logic.models.Meal
import logic.usecase.SuggestEasyMealsUseCase
import presentation.utils.tryToExecute
import presentation.utils.withGreenColor

class SuggestEasyMealsUI(
    private val suggestEasyMealsUseCase: SuggestEasyMealsUseCase
) : BaseUIController {
    override fun execute() {
        tryToExecute(
            action = suggestEasyMealsUseCase::suggestEasyMeals,
            onSuccess = ::outputSuggestEasyMeals
        )
    }

    private fun outputSuggestEasyMeals(meals: List<Meal>) {
        println("Suggest Easy Meal ".withGreenColor())
        meals.forEachIndexed { index, meal ->
            println("${index + 1} - Meal Name: ${meal.name},   ".withGreenColor())
        }
    }

}
