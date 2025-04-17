package presentation

import logic.usecase.SearchByCaloriesAndProteinUseCase
import presentation.uiController.BaseUIController
import presentation.utils.tryToExecute
import presentation.utils.withGreenColor

class GymHelperUI(private val searchByCaloriesAndProteinUseCase: SearchByCaloriesAndProteinUseCase): BaseUIController {

    override fun execute() {
        tryToExecute (
            action = {startGymHelper()},
            onSuccess = {
                println("enjoy your meal!".withGreenColor())
            }
        )
    }

    fun startGymHelper() {
        println("Hi, Welcome to your Gym Helper!")
        val calories = readValidInt("Please enter your desired calories:")
        val protein = readValidInt("Please enter your desired protein:")
        val meals = searchByCaloriesAndProteinUseCase.getMealByCaloriesAndProtein(calories, protein)
        meals.forEach {
            println("Meal: ${it.name}," +
                    " Calories: ${it.nutrition?.calories}," +
                    " Protein: ${it.nutrition?.protein}")
        }
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