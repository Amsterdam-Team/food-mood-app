package presentation.uiController

import logic.models.Meal
import logic.usecase.GetMealsByAddedDateUseCase
import presentation.utils.tryToExecute
import presentation.utils.withGreenColor
import presentation.utils.withRedColor
import presentation.utils.withYellowColor

class MealsByDateUIController(private val getMealsByAddedDateUseCase: GetMealsByAddedDateUseCase) : BaseUIController {
    override fun execute() {
        var success = false
        while (!success) {
            println("Please enter the date".withYellowColor())
            val date = readlnOrNull().toString()
            tryToExecute(
                action = {
                    getMealsByAddedDateUseCase.getMealsByDate(date)
                },
                onSuccess = {
                    onGetMealByDateSuccess(it)
                    success = true
                }
            )
        }
    }

    private fun onGetMealByDateSuccess(meals: List<Meal>) {
        println("Here is all the meals founded in that date ${meals.toPairNameId()}".withGreenColor())

        var isIdExist = false
        while (!isIdExist) {
            println("please enter id for the meal you want to know more".withYellowColor())
            val id = readlnOrNull().toString()

            try {
                val mealDetails = getMealsByAddedDateUseCase.getDetailedMealFromMealsData(id, meals)
                println("Here is the full details for your meal: $mealDetails".withGreenColor())
                isIdExist = true
            } catch (e: Exception) {
                println("id is not exist".withRedColor())
            }
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
