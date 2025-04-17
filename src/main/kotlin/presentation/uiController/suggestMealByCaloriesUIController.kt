package presentation.uiController

import logic.models.Meal
import logic.usecase.SuggestAMealByCaloriesUseCase
import presentation.utils.tryToExecute

class suggestMealByCaloriesUIController(private val suggestAMealByCaloriesUseCase: SuggestAMealByCaloriesUseCase):BaseUIController {
    override fun execute() {
        tryToExecute(
            action = {startSuggestMealByCalories()},
            onSuccess = { println("Enjoy your meal") }
        )
        }

    private fun startSuggestMealByCalories() {
        var currentMeal = suggestAMealByCaloriesUseCase.getMealRandomly()
        val nextMeal = suggestAMealByCaloriesUseCase.getAnotherRandomMeal(currentMeal)
        while (true) {
            println(
                "Here is a meal contains more than 700 calorie:" +
                        " \n name of meal is ${currentMeal.name}" +
                        " \n its description is :${currentMeal.description}"
            )
            println(
                "Do you like it and want more details about it?" +
                        " \n enter Y to get details or N to get another meal"
            )
            when (readlnOrNull()?.lowercase()) {
                "y" -> println(currentMeal)
                "n " -> {
                    println(
                        "Here is another meal over 700 calorie" +
                                "\n name of meal is ${nextMeal.name}" +
                                " \n its description is :${nextMeal.description}"
                    )

                    currentMeal = nextMeal
                }

                else -> {
                    println("Please enter y or n")
                }
            }
        }

    }
}