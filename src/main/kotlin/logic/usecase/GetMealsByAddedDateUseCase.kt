package logic.usecase

import logic.MealsRepository
import logic.exception.FoodMoodException
import logic.models.Meal
import presentation.utils.Utils

class GetMealsByAddedDateUseCase(private val mealsRepository: MealsRepository) {

    private fun getMealsByDate(inputDate: String): List<Meal> {
        val allMeals = mealsRepository.getAllMeals()
        val filteredMealByDate =allMeals .filter { currentMeal ->
            currentMeal.submittedDate == Utils.parseDate(inputDate)
        }
        filteredMealByDate.ifEmpty {
            throw FoodMoodException.Validation.NoMealsWereFoundForTheGivenDate
        }
        return filteredMealByDate
    }

    fun getDetailedMealFromFilteredList(inputId: String, list: List<Meal>): Meal {
        val requestedMeal = list.find { meal ->
            meal.id == inputId
        } ?: throw FoodMoodException.Validation.NoMealsWereFoundForTheGivenDate
        return requestedMeal
    }
}


fun List<Meal>.toPairNameId() = mapNotNull { meal ->
    meal.name?.let { name ->
        meal.id?.let { id ->
            name to id
        }
    }

}


