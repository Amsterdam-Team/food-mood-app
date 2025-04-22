package logic.helpers

import logic.models.Nutrition

object FastHealthyMealTestFactory {
    private const val NEGATIVE_PREPARATION_TIME = -10
    private const val ZERO_PREPARATION_TIME = 0
    const val PREPARATION_TIME_IN_TARGET = 15
    const val PREPARATION_TIME_ABOVE_TARGET = 30
    val preparationTimeRange = 1..15

    val defaultNutrition = Nutrition(
        totalFat = 30.0,
        saturatedFat = 5.0,
        carbohydrates = 10.0,
        calories = null,
        sugar = null,
        sodium = null,
        protein = null,
    )

    val mealWithAboveTimeTarget =
        createMeal().copy(nutrition = defaultNutrition, preparationTime = PREPARATION_TIME_ABOVE_TARGET)

    val mealWithNegativePreparationTime =
        createMeal().copy(nutrition = defaultNutrition, preparationTime = NEGATIVE_PREPARATION_TIME)

    val mealWithZeroPreparationTime =
        createMeal().copy(nutrition = defaultNutrition, preparationTime = ZERO_PREPARATION_TIME)

    val mealWithNotHealthyNutrition =
        createMeal().copy(
            preparationTime = PREPARATION_TIME_IN_TARGET,
            nutrition = defaultNutrition.copy(totalFat = 80.0, saturatedFat = 15.0, carbohydrates = 50.0)
        )
}
