package presentation.uiController

import logic.usecase.SearchByCaloriesAndProteinUseCase
import presentation.utils.tryToExecute
import presentation.utils.withGreenColor

class GymHelperUIController(private val searchByCaloriesAndProteinUseCase: SearchByCaloriesAndProteinUseCase): BaseUIController {

    override fun execute() {
        println("Hi, Welcome to your Gym Helper!")
        val calories = readValidInt("Please enter your desired calories:")
        val protein = readValidInt("Please enter your desired protein:")

        tryToExecute(
            action = {
                searchByCaloriesAndProteinUseCase.getMealByCaloriesAndProtein(calories, protein)
            },
            onSuccess = { meals ->
                meals.forEach {
                    println("Meal: ${it.name}," +
                            " Calories: ${it.nutrition?.calories}," +
                            " Protein: ${it.nutrition?.protein}")
                }
                println("enjoy your meal!".withGreenColor())
            }
        )
    }

    fun readValidInt(prompt: String): Int {
        while (true) {
            println(prompt)
            val input = readlnOrNull()?.toIntOrNull()
            if (input != null) return input
            println("Invalid input. Please enter a valid number.")
        }
    }


}