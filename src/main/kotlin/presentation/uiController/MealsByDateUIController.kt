package presentation.uiController

import logic.models.Meal
import logic.usecase.GetMealsByAddedDateUseCase
import presentation.utils.tryToExecute

class MealsByDateUIController(private val getMealsByAddedDateUseCase: GetMealsByAddedDateUseCase) : BaseUIController {
    override fun execute() {
        tryToExecute(
            action = {
                println("Please enter the date")
                val date = readlnOrNull().toString()
                getMealsByAddedDateUseCase.getMealsByDate(date)
            },
            onSuccess = ::onGetGetMealByDateSuccess

        )
    }

    private fun onGetGetMealByDateSuccess(meals: List<Meal>) {
        println("Here is all the meals founded in that date ${meals.toPairNameId()}")
        println("please enter id for the meal you want to know more")
        val id = readlnOrNull().toString()
        println(
            "Here is the full details for ur meal ${
                getMealsByAddedDateUseCase.getDetailedMealFromMealsData(
                    id,
                    meals
                )
            }"
        )
    }

}

fun List<Meal>.toPairNameId() = mapNotNull { meal ->
    meal.name?.let { name ->
        meal.id?.let { id ->
            name to id
        }
    }
}
