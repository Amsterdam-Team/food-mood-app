package presentation.uiController

import logic.models.Meal
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
        printMealDetails(mealDetails)
    }

    private fun printMealDetails(meal : List<Meal>){
        meal.forEach { meal->
            meal.id?.let { println("  ID: $it") }
            meal.name?.let { println("  Name: $it") }
            meal.description?.let { println("  Description: $it") }
            meal.preparationTime?.let { println("  Preparation Time: $it") }
            meal.contributorId?.let { println(" Contributor Id: $it") }
            meal.submittedDate?.let { println("  Submitted Date: $it") }
            meal.tags?.let { println("  Tags: $it") }
            meal.nutrition?.let { println("  Nutrition: $it") }
            meal.steps?.let { println("  Steps: $it") }
            meal.numberOfSteps?.let { println("  Number Of Step: $it") }
            meal.ingredients?.let { println("  Ingredients: $it") }
            meal.numberOfIngredients?.let { println(" Number Of Ingredients: $it") }
        }
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