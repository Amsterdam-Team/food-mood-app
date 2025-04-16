package presentation

import logic.ketoMealHelper.GetRandomKetoMealsUseCase
import logic.models.Meal
import presentation.uiController.BaseUIController
import presentation.utils.tryToExecute

class KetoMealHelperUIController(private val getKetoFriendlyMealsUseCase: GetRandomKetoMealsUseCase) :
    BaseUIController {

    private lateinit var suggestedMeal: Meal
    private var ketoMeals = getKetoFriendlyMealsUseCase.getKetoMeals()
    private var input: String = ""

    override fun execute() {
        tryToExecute(
            action = { getKetoFriendlyMealsUseCase.getKetoMealRandomly(ketoMeals) },
            onSuccess = { onGetRandomMealSuccess(it) }
        )
    }

    private fun onGetRandomMealSuccess(meal: Meal) {
        suggestedMeal = meal
        println("Suggested Meal: ${meal.name}")
        ketoMeals = ketoMeals.filterNot { it == meal }
        handleUserInput()

    }

    private fun handleUserInput() {
        println("press Y if you like it , or N if you do not like it")
        input = readlnOrNull()?.trim()?.lowercase() ?: ""
        when (input) {
            "y" -> println(suggestedMeal.toString())
            "n" -> {
                execute()
            }

            else -> {
                println("invalid input :(")
                handleUserInput()
            }
        }

    }

}
