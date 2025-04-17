package presentation.uiController

import logic.usecase.GetMealByNameUseCase
import presentation.utils.tryToExecute
import presentation.utils.withGreenColor

class GetMealByNameUIController(
    private val getMealByNameUseCase: GetMealByNameUseCase
): BaseUIController {

    fun getMealByNameUI(){
        println("Welcome to the Meal Finder! 🍽️")
        print("Type the name of the meal you're looking for to show it's details :\n> ")
        val mealName = readLine()?.trim() ?: ""

        println("\nSearching...\n")
        val mealDetails = getMealByNameUseCase.getMealDetails(mealName)

        println("🔍 Results:")
        mealDetails.forEach { println("- $it") }
    }

    override fun execute() {
        tryToExecute(
            action = {
                getMealByNameUI()
            },
            onSuccess = {
                println("Success, This is Your Meal ..".withGreenColor())
            }
        )
    }
}