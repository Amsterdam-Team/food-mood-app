package presentation.uiController

import logic.models.Meal
import logic.sweetsWithNoEggs.GetRandomOneSweetMealWithoutEggsUseCase
import presentation.utils.tryToExecute
import presentation.utils.withGreenColor

class SweetMealsUIController(
    private val getRandomOneSweetMealWithoutEggsUseCase: GetRandomOneSweetMealWithoutEggsUseCase
) : BaseUIController {

    fun startSuggestShowSweetMealWithoutEggesUI() {
        println("Hi, Welcome to your Sweet Helper!")

        var currentSweetMeal = getRandomOneSweetMealWithoutEggsUseCase.getRandomOneSweetMealWithoutEggs()
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
                    val nextMeal = getRandomOneSweetMealWithoutEggsUseCase.getAnotherRandomMeal(currentSweetMeal)
                    if (nextMeal == null) {
                        println("No more sweet meals available without eggs")
                        break
                    }
                    currentSweetMeal = nextMeal
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

    override fun execute() {
        tryToExecute(
            action = {
                startSuggestShowSweetMealWithoutEggesUI()
            },
            onSuccess = {
                println("Success! Enjoy your sweet meal!".withGreenColor())
            }
        )
    }
}