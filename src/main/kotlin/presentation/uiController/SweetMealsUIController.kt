package presentation.uiController

import logic.models.Meal
import logic.usecase.GetRandomOneSweetMealWithoutEggsUseCase
import presentation.utils.tryToExecute
import presentation.utils.withGreenColor

class SweetMealsUIController(
    private val getRandomOneSweetMealWithoutEggsUseCase: GetRandomOneSweetMealWithoutEggsUseCase
) : BaseUIController {
    override fun execute() {
        tryToExecute(
            action = getRandomOneSweetMealWithoutEggsUseCase::getRandomOneSweetMealWithoutEggs,
            onSuccess = ::onGetSuggestShowSweetMealWithoutEggsUISuccess
        )
    }

    fun onGetSuggestShowSweetMealWithoutEggsUISuccess(currentSweetMeal: Meal) {
        println("Here is a random sweet meal without eggs: ${currentSweetMeal.name} , And description: ${currentSweetMeal.description}")
        readFromUser(currentSweetMeal)
    }
    private fun readFromUser(currentSweetMeal: Meal) {
        println("You Are like it? y/n")
        when (readlnOrNull()?.lowercase()) {
            "y" -> {
                println("Great! Enjoy your meal!")
                println(currentSweetMeal.toString().withGreenColor())
            }
            "n" -> {
                execute()
            }

            else -> {
                println("Please enter 'y' or 'n'")
                readFromUser(currentSweetMeal)
            }
        }
    }
}