package presentation

import org.example.logic.usecase.SearchByCaloriesAndProteinUseCase

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
            println("No meals found that match your desired values.")
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