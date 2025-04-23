package logic.usecase

import logic.MealsRepository
import logic.exception.FoodMoodException
import logic.models.Meal
import presentation.utils.getRandomElementOrNull

class GetKetoMealsUseCase(
    private val mealsRepository: MealsRepository
) {

    fun getRandomKetoMeal(): Meal {
        return getKetoMeals().getRandomElementOrNull() ?: throw FoodMoodException.Validation.EmptyDataException
    }

    fun getKetoMeals(): List<Meal> {
        return mealsRepository.getAllMeals().filter(::isKetoFriendlyMeal)
    }

    private fun isKetoFriendlyMeal(meal: Meal): Boolean {
        return meal.nutrition?.totalFat?.let { it >= 20.0 } ?: false
                && meal.nutrition?.carbohydrates?.let { it in 0.0..10.0 } ?: false
                && meal.nutrition?.protein?.let { it in 15.0..30.0 } ?: false
                && meal.description != null
    }

}