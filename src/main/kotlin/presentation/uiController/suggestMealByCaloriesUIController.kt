package presentation.uiController

import logic.models.Meal
import logic.usecase.SuggestAMealByCaloriesUseCase
import presentation.utils.tryToExecute

class suggestMealByCaloriesUIController(private val suggestAMealByCaloriesUseCase: SuggestAMealByCaloriesUseCase):BaseUIController {
    override fun execute() {
        tryToExecute(
            action = {startSuggestMealByCalories()},
            onSuccess = { println("Enjoy your meal") }
        )
        }

    private fun startSuggestMealByCalories(){
        var currentMeal = suggestAMealByCaloriesUseCase.getMealRandomly()
        val nextMeal =suggestAMealByCaloriesUseCase.getAnotherRandomMeal(currentMeal)
        while (true){
            println("Here is a meal contains more than 700 calorie:" +
                    " \n name of meal is ${currentMeal.name}" +
                    " \n its description is :${currentMeal.description}")
            println("Do you like it and want more details about it?" +
                    " \n enter Y to get details or N to get another meal")
            when (readlnOrNull()?.lowercase()){
                "y" -> println(currentMeal)
                "n " -> {
                    println("Here is another meal over 700 calorie" +
                        "\n name of meal is ${nextMeal.name}" +
                    " \n its description is :${nextMeal.description}")

                    currentMeal = nextMeal
            }
                else -> {
                    println("Please enter y or n")
                }
        }
    }

    }

/*
* package presentation.uiController
class MealsByDateUIController(private val getMealsByAddedDateUseCase: GetMealsByAddedDateUseCase):BaseUIController{
    override fun execute() {
        tryToExecute(
            action ={startGetMealByDate()},
            onSuccess = { println("enjoy your meal") }
        )
    }

    private fun startGetMealByDate() {
        println("Please enter the date")
        val date = readlnOrNull().toString()
        val allMeals = getMealsByAddedDateUseCase.getMealsByDate(date)
        while (true) {
            println("Here is all the meals founded in that date ${allMeals.toPairNameId()}")
            println("please enter id for the meal you want to know more")
            val id = readlnOrNull().toString()
            println("Here is the full details for ur meal ${getMealsByAddedDateUseCase.getDetailedMealFromMealsData(id,allMeals)}")
        }
    }

}

*/
fun List<Meal>.toPairNameDescription() = mapNotNull { meal ->
    meal.name?.let { name ->
        meal.description?.let { description ->
            name to description
        }
    }
}