package logic.usecase

import logic.MealsRepository
import logic.Utils.parseDate
import logic.exception.FoodMoodException
import logic.models.Meal

class GetMealsByAddedDateUseCase(private val mealsRepository: MealsRepository) {

    fun getMealsByDate(inputDate: String): List<Meal> {
        val allMeals = mealsRepository.getAllMeals()
        val filteredMealByDate = allMeals.filter { currentMeal ->
            currentMeal.submittedDate == parseDate(inputDate)
        }

        return filteredMealByDate.ifEmpty { throw FoodMoodException.Validation.NoMealsWereFoundForTheGivenDate }
    }

    fun getDetailedMealFromMealsData(mealId: String, meals: List<Meal>): Meal {
        val requestedMeal = meals.find { meal ->
            meal.id == mealId
        } ?: throw FoodMoodException.Validation.EmptyDataException
        return requestedMeal
    }
}







