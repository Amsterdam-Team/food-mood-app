package presentation.uiController.helpers

import logic.helpers.createMeal
import logic.models.Nutrition
import java.io.ByteArrayOutputStream
import java.io.PrintStream

object FastHealthyMealsUIControllerTestFactory {
    private const val FAST_PREPARATION_TIME = 10
    const val EMPTY_DATA_EXCEPTION_ERROR_MESSAGE = "No meals found that match your criteria."
    const val UNEXPECTED_EXCEPTION_ERROR_MESSAGE = "An unexpected error occurred. Please try again later."

    private val nutrition = Nutrition(
        totalFat = 30.0,
        saturatedFat = 5.0,
        carbohydrates = 10.0,
        calories = null,
        sugar = null,
        sodium = null,
        protein = null,
    )

    val fastPreparationMeal =
        createMeal().copy(nutrition = nutrition, preparationTime = FAST_PREPARATION_TIME)

    val formattedFastPreparationMeal = "Meal(id=${fastPreparationMeal.id}, " +
            "name=${fastPreparationMeal.name}, " +
            "description=${fastPreparationMeal.description}, " +
            "preparationTime=${fastPreparationMeal.preparationTime}, " +
            "contributorId=${fastPreparationMeal.contributorId}, " +
            "submittedDate=${fastPreparationMeal.submittedDate}, " +
            "tags=${fastPreparationMeal.tags}, " +
            "nutrition=${fastPreparationMeal.nutrition}, " +
            "steps=${fastPreparationMeal.steps}, " +
            "numberOfSteps=${fastPreparationMeal.numberOfSteps}, " +
            "ingredients=${fastPreparationMeal.ingredients}, " +
            "numberOfIngredients=${fastPreparationMeal.numberOfIngredients})"

    fun readConsoleOutputContent(): ByteArrayOutputStream {
        val outContent = ByteArrayOutputStream()
        System.setOut(PrintStream(outContent))
        return outContent
    }
}