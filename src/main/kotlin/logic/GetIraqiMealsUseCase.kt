package logic

import logic.exception.FoodMoodException
import logic.models.Meal

class GetIraqiMealsUseCase(
    private val mealsRepository: MealsRepository
) {
    fun getOnlyIraqiMeals(): List<Meal>{
        val meals = mealsRepository.getAllMeals()
            .filter(::isHighQualityData)
            .filter(::isIraqiMeal)
        if (meals.isNotEmpty()){
            return meals
        } else{
            throw FoodMoodException.EmptyDataException
        }
    }

    private fun isIraqiMeal(meal: Meal): Boolean{
        return meal.description?.contains("Iraq", ignoreCase = true)?: false || meal.tags?.contains("iraqi")?: false
    }
    private fun isHighQualityData(meal: Meal): Boolean{
        return meal.description != null && meal.tags != null
    }
}