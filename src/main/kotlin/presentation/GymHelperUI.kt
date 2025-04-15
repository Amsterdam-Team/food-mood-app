package org.example.presentation

import org.example.logic.SearchByCaloriesAndProteinUseCase
import org.example.models.ResultStatus

class GymHelperUI(private val searchByCaloriesAndProteinUseCase: SearchByCaloriesAndProteinUseCase) {
    fun start(){
        println("Hi, Welcome to your Gym Helper!")

        val calories = readValidInt("Please enter your desired calories:")
        val protein = readValidInt("Please enter your desired protein:")

        val result = searchByCaloriesAndProteinUseCase.getMealByCaloriesAndProtein(calories, protein)

        when (result) {
            is ResultStatus.Error -> println("An error occurred: ${result.exception.message}")
            is ResultStatus.Loading -> println("Loading meals... Please wait.")
            is ResultStatus.Success -> {
                val meals = result.meals
                if (meals.isNotEmpty()) {
                    println("Here are the meals that match your desired calories and protein:")
                    meals.forEach { println("Meal: ${it.name}, Calories: ${it.nutrition?.calories}, Protein: ${it.nutrition?.protein}") }
                } else {
                    println("No meals found that match your desired values.")
                }
            }
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