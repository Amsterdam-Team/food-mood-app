package logic.usecase

import data.KetoMealsDataStore
import logic.MealsRepository
import logic.exception.FoodMoodException
import logic.models.Meal
import presentation.utils.getRandomElementOrNull

class GetRandomKetoMealUseCase(
    private val mealsRepository: MealsRepository,
    private val ketoMealsDataStore: KetoMealsDataStore
) {

    fun getRandomKetoMeal(): Meal {
        val randomMeal =
            getKetoMeals().getRandomElementOrNull() ?: throw FoodMoodException.Validation.EmptyDataException
        if (getKetoMeals().size == ketoMealsDataStore.checkTotalSeenKetoMeals()) {
            throw FoodMoodException.Validation.NoMoreKetoMeals
        }
        if (ketoMealsDataStore.checkSeenKetoMeal(randomMeal)) {
            getRandomKetoMeal()
        } else {
            ketoMealsDataStore.addSeenKetoMeal(randomMeal)
        }
        return randomMeal

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