package presentation.uiController

import logic.models.Meal
import logic.usecase.SuggestAMealByCaloriesUseCase
import presentation.utils.tryToExecute

class SuggestMealByCaloriesUIController(private val suggestAMealByCaloriesUseCase: SuggestAMealByCaloriesUseCase) :
    BaseUIController {
    override fun execute() {
        tryToExecute(
            action = suggestAMealByCaloriesUseCase::getMealRandomly,
            onSuccess = ::onGetSuggestedMealSuccess
        )
    }

    fun onGetSuggestedMealSuccess(meal: Meal) {
        println(
            "Here is a meal contains more than 700 calorie:" +
                    " \n name of meal is ${meal.name}" +
                    " \n its description is :${meal.description}"
        )
        println(
            "Do you like it and want more details about it?" +
                    " \n enter Y to get details or N to get another meal"
        )
        do {
           val userChoice = readlnOrNull()?.lowercase()
            when (userChoice) {
                "y" -> println(meal)
                "n" -> execute()
                else -> println("Please enter y or n")
            }
        } while (userChoice != "y" && userChoice != "n")
    }
}

