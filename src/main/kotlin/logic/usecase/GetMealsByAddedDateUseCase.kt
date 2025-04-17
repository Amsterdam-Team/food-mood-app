package logic.usecase

import logic.MealsRepository
import logic.exception.FoodMoodException
import logic.models.Meal
import logic.Utils.parseDate

class GetMealsByAddedDateUseCase(private val mealsRepository: MealsRepository) {

     fun getMealsByDate(inputDate: String): List<Meal> {
        val allMeals = mealsRepository.getAllMeals()
        val filteredMealByDate =allMeals .filter { currentMeal ->
            currentMeal.submittedDate == parseDate(inputDate)
        }
        filteredMealByDate.ifEmpty {
            throw FoodMoodException.Validation.NoMealsWereFoundForTheGivenDate
        }
        return filteredMealByDate
    }

    fun getDetailedMealFromMealsData(mealId: String, meals: List<Meal>): Meal {
        val requestedMeal = meals.find { meal ->
            meal.id == mealId
        } ?: throw FoodMoodException.Validation.NoMealsWereFoundForTheGivenDate
        return requestedMeal
    }
}







