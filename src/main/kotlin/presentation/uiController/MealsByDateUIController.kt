package presentation.uiController

import logic.models.Meal
import logic.usecase.GetMealsByAddedDateUseCase
import presentation.utils.tryToExecute

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
fun List<Meal>.toPairNameId() = mapNotNull { meal ->
    meal.name?.let { name ->
        meal.id?.let { id ->
            name to id
        }
    }
}
