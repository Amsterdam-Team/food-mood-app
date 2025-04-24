package presentation.uiController

import logic.models.Meal
import logic.usecase.GetRandomKetoMealUseCase
import presentation.utils.tryToExecute
import presentation.utils.withGreenColor
import presentation.utils.withRedColor

class KetoMealHelperUIController(private val getRandomKetoMealUseCase: GetRandomKetoMealUseCase) :
    BaseUIController {


    private var input: String = ""
    lateinit var currentMeal: Meal

    override fun execute() {
        tryToExecute(
            action = { getRandomKetoMealUseCase.getRandomKetoMeal() },
            onSuccess = { onGetRandomMealSuccess(it) }
        )
    }

    private fun onGetRandomMealSuccess(meal: Meal) {
        currentMeal = meal
        println(meal.name)
        handleUserInput()
    }


    private fun handleUserInput() {
        println("press Y if you like it , or N if you do not like it")
        input = readlnOrNull()?.trim()?.lowercase() ?: ""
        when (input) {
            "y" -> println(currentMeal.toString().withGreenColor())
            "n" -> {
                execute()
            }

            else -> {
                println("invalid input :(".withRedColor())
                handleUserInput()
            }
        }

    }

}
