package presentation

import logic.exception.FoodMoodException
import logic.ketoMealHelper.GetKetoMealsUseCase
import logic.models.Meal
import org.example.presentation.utils.getRandomElementOrNull
import presentation.uiController.BaseUIController
import presentation.utils.tryToExecute
import presentation.utils.withGreenColor
import presentation.utils.withRedColor

class KetoMealHelperUIController(private val getKetoFriendlyMealsUseCase: GetKetoMealsUseCase) :
    BaseUIController {

    private lateinit var suggestedMeal: Meal
    private val seenMeals: MutableList<Meal> = mutableListOf()
    private val ketoMeals = getKetoFriendlyMealsUseCase.getKetoMeals()
    private var input: String = ""

    override fun execute() {
        tryToExecute(
            action = { ketoMeals.getRandomElementOrNull() ?: throw FoodMoodException.EmptyDataException },
            onSuccess = { onGetRandomMealSuccess(it) }
        )
    }

    private fun onGetRandomMealSuccess(meal: Meal) {
        suggestedMeal = meal
        if (seenMeals.size == ketoMeals.size) {
            println("No More Keto Meals".withRedColor())
            return
        }
        if (suggestedMeal in seenMeals) {
            execute()
        } else {
            println("Suggested Meal: ${meal.name}")
            seenMeals.add(suggestedMeal)
            handleUserInput()

        }

    }

    private fun handleUserInput() {
        println("press Y if you like it , or N if you do not like it")
        input = readlnOrNull()?.trim()?.lowercase() ?: ""
        when (input) {
            "y" -> println(suggestedMeal.toString().withGreenColor())
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
