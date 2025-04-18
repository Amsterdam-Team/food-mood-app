package presentation.uiController

import logic.models.Meal
import logic.sweetsWithNoEggs.GetRandomOneSweetMealWithoutEggsUseCase
import presentation.utils.tryToExecute

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
        println("Hi, Welcome to your Sweet Helper!")

        //  var currentSweetMeal = getRandomOneSweetMealWithoutEggsUseCase.getRandomOneSweetMealWithoutEggs()
        while (true) {
            println("Here is a random sweet meal without eggs: ${currentSweetMeal.name} , And description: ${currentSweetMeal.description}")
            println("You Are like it? y/n")
            when (readlnOrNull()?.lowercase()) {
                "y" -> {
                    println("Great! Enjoy your meal!")
                    showMeal(currentSweetMeal)
                    break
                }
                "n" -> {
                    execute()
                }

                else -> {
                    println("Please enter 'y' or 'n'")
                }
            }
        }

    }

    private fun showMeal(firstSweetMeal: Meal) {
        println(firstSweetMeal)
    }


}