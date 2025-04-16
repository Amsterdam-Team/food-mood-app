package logic.sweetsWithNoEggs

import logic.MealsRepository
import logic.exception.FoodMoodException.Validation.*
import logic.models.Meal
import org.example.presentation.utils.getRandomElementOrNull

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

    fun getRandomOneSweetMealWithoutEggs(): Meal {
        return sweetMeals.ifEmpty {
            throw MealNotFounded
        }
            .getRandomElementOrNull() ?: throw MealNotFounded
    }

    fun getAnotherRandomMeal(meal: Meal?): Meal? {
        val remainingMeals = sweetMeals.filter { it != meal }
        return remainingMeals.getRandomElementOrNull()
    }
}