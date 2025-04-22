package logic.helpers
import logic.models.Meal
import logic.models.Nutrition

object SeafoodMealTestFactory {

    private const val FISH_TAG = "seafood"

    private val defaultNutrition = Nutrition(
        totalFat = null,
        saturatedFat = null,
        carbohydrates = null,
        calories = null,
        sugar = null,
        sodium = null,
        protein = null,
    )

    fun createSeafoodMeal(name: String? = null, protein: Double? = null): Meal {
        return createMeal(
            name = name,
            nutrition = defaultNutrition.copy(protein = protein),
            tags = listOf(FISH_TAG)
        )
    }

    val validTunaMeal = createSeafoodMeal(name = "Tuna", protein = 15.0)
    val validShrimpMeal = createSeafoodMeal(name = "Shrimp", protein = 20.0)
    val validSalmonMeal = createSeafoodMeal(name = "Salmon", protein = 25.0)
}