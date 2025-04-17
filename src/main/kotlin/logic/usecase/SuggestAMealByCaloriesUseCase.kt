package logic.usecase

import logic.MealsRepository
import logic.exception.FoodMoodException
import logic.models.Meal
import org.example.presentation.utils.getRandomElementOrNull

class SuggestAMealByCaloriesUseCase(private val mealsRepository: MealsRepository) {
        private val inputCalories = 700.0
        private val filteredMealByCalories:List<Meal> by lazy {
            mealsRepository.getAllMeals().filter {
                (it.nutrition?.calories ?: 0.0) > inputCalories
            }
        }

    fun getMealRandomly():Meal {
        return filteredMealByCalories.ifEmpty {
            throw FoodMoodException.Validation.EmptyDataException }
            .getRandomElementOrNull() ?: throw FoodMoodException.Validation.EmptyDataException
    }

    fun getAnotherRandomMeal(currentMeal:Meal):Meal{
        val remainingMeals = filteredMealByCalories.filter { nextMeal ->
            nextMeal != currentMeal
        }
        return remainingMeals.getRandomElementOrNull() ?: throw FoodMoodException.Validation.EmptyDataException

    }

}
