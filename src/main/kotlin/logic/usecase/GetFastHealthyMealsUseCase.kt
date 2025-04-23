package logic.usecase


import logic.MealsRepository
import logic.exception.FoodMoodException
import logic.models.Meal

class GetFastHealthyMealsUseCase(
    private val mealsRepository: MealsRepository
) {

    fun getFastHealthMeals(): List<Meal> {
        val allMeals = mealsRepository.getAllMeals()
        val validMeals = getValidMeals(allMeals).ifEmpty { throw FoodMoodException.Validation.EmptyDataException }

        return filterHealthyMeals(validMeals).ifEmpty { throw FoodMoodException.Validation.EmptyDataException }
    }

    private fun getValidMeals(meals: List<Meal>): List<Meal> {
        return meals.filter { it.nutrition != null && it.preparationTime != null }
    }

    private fun filterHealthyMeals(meals: List<Meal>): List<Meal> {
        val totalFatAvg = meals.mapNotNull { it.nutrition?.totalFat }.average()
        val satFatAvg = meals.mapNotNull { it.nutrition?.saturatedFat }.average()
        val carbsAvg = meals.mapNotNull { it.nutrition?.carbohydrates }.average()

        val healthyMeals = meals.filter { meal ->
            val nutrition = meal.nutrition!!
            meal.preparationTime!! <= PREPARATION_TIME_TARGET &&
                    isBelowAverage(nutrition.totalFat, totalFatAvg) &&
                    isBelowAverage(nutrition.saturatedFat, satFatAvg) &&
                    isBelowAverage(nutrition.carbohydrates, carbsAvg)
        }

        return healthyMeals
    }

    private fun isBelowAverage(selector: Double?, average: Double, default: Double = 0.0) =
        (selector ?: default) < average


    private companion object {
        const val PREPARATION_TIME_TARGET = 15

    }
}