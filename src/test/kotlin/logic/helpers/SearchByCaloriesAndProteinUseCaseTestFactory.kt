package logic.helpers

object SearchByCaloriesAndProteinUseCaseTestFactory {
    val steak = createMealByProteinAndCalories("steak", 250.0, 25.0)
    val pizza = createMealByProteinAndCalories("pizza", 400.0, 50.0)
    val cucumber = createMealByProteinAndCalories("cucumber", 23.0, 1.0)
    val chicken = createMealByProteinAndCalories("chicken", 249.0, 24.0)
    val water = createMealByProteinAndCalories("water", 0.0, 0.0)

    val nullNutritionMeal = createMeal(name = "pizza")
    val nullCaloriesMeal = createMealByProteinAndCalories("pizza", calories = null, protein = 20.0)
    val nullProteinMeal = createMealByProteinAndCalories("MissingProtein", calories = 200.0, protein = null)
    val validMeal = createMealByProteinAndCalories("ValidMeal", 200.0, 20.0)
}