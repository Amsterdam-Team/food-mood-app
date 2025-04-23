package logic.helpers

import logic.models.Meal
import logic.models.Nutrition
import java.io.ByteArrayOutputStream
import java.io.PrintStream

object SeafoodMealsSuccessUIControllerTestFactory {

    private const val FISH_TAG = "seafood"
    const val EMPTY_DATA_EXCEPTION_ERROR_MESSAGE = "No meals found that match your criteria."
    const val UNEXPECTED_EXCEPTION_ERROR_MESSAGE = "An unexpected error occurred. Please try again later."
    const val SHRIMP = "Shrimp"
    const val TUNA = "Tuna"

    val shrimpMeal = createSeafoodMeal(name = SHRIMP, protein = 20.0)
    val tunaMeal = createSeafoodMeal(name = TUNA, protein = 15.0)

    val formattedShrimpMeal = "Rank: 1 Meal Name: ${shrimpMeal.name} Protein Amount: ${shrimpMeal.nutrition?.protein}"

    fun readConsoleOutputContent(): ByteArrayOutputStream {
        val outContent = ByteArrayOutputStream()
        System.setOut(PrintStream(outContent))
        return outContent
    }

    private fun createSeafoodMeal(name: String, protein: Double): Meal {
        val nutrition = Nutrition(
            protein = protein,
            totalFat = null, saturatedFat = null, carbohydrates = null, calories = 10.0, sugar = null, sodium = null,
        )
        return createMeal(
            name = name,
            nutrition = nutrition,
            tags = listOf(FISH_TAG)
        )
    }

}