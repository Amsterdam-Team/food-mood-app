package logic.usecase

import logic.MealsRepository
import logic.exception.FoodMoodException.Validation.*
import logic.models.Meal
import presentation.utils.getRandomElementOrNull

class GetRandomOneSweetMealWithoutEggsUseCase(
    private val mealsRepository: MealsRepository
) {

    private val sweetMeals: List<Meal> by lazy {
        mealsRepository.getAllMeals()
            .ifEmpty { throw MealNotFounded }
            .filter { currentMeal ->
                val tags = currentMeal.tags?.map { it.lowercase() } ?: emptyList()
                val ingredients = currentMeal.ingredients?.map { it.lowercase() } ?: emptyList()
                "sweet" in tags && "eggs" !in ingredients
            }
    }

    private val remainingMeals = sweetMeals.toMutableList()


    fun getRandomOneSweetMealWithoutEggs(): Meal {
        if (remainingMeals.isEmpty()) throw MealNotFounded
        val meal = remainingMeals.getRandomElementOrNull()?:throw MealNotFounded
        remainingMeals.remove(meal)
        return meal
    }
}