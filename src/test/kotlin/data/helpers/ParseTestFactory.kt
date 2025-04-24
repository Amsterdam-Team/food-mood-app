package data.helpers

import logic.models.Nutrition

object ParseTestFactory {
    val nutrition_sample = Nutrition(
        calories = 0.3,
        totalFat = 0.1,
        sugar = 0.0,
        sodium = 1.2,
        protein = 2.2,
        saturatedFat = 0.01,
        carbohydrates = 0.0,
    )
}