package presentation

import logic.usecase.SearchByCaloriesAndProteinUseCase
import org.example.logic.EmptyDataException

class GymHelperUI(private val searchByCaloriesAndProteinUseCase: SearchByCaloriesAndProteinUseCase) {
    fun start() {
        println("Hi, Welcome to your Gym Helper!")

        val calories = readValidInt("Please enter your desired calories:")
        val protein = readValidInt("Please enter your desired protein:")

        val meals = searchByCaloriesAndProteinUseCase.getMealByCaloriesAndProtein(calories, protein)

        if (meals.isNotEmpty()) {
            println("Here are the meals that match your desired calories and protein:")
            meals.forEach { println("Meal: ${it.name}, Calories: ${it.nutrition?.calories}, Protein: ${it.nutrition?.protein}") }
        } else {
            throw EmptyDataException()
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