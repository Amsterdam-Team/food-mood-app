package presentation.uiController.helpers

import logic.helpers.createMeal
import logic.helpers.createNutrition
import logic.models.Meal
import java.io.ByteArrayInputStream

object MealsByDateUIControllerTestFactory {

    const val TEST_MEAL_ID = "meal123"

    fun createTestMeal(): Meal {
        return createMeal(
            id = TEST_MEAL_ID,
            name = "Test Meal",
            description = "Test Description",
            preparationTime = 30,
            nutrition = createNutrition(calories = 500.0, protein = 20.0),
            ingredients = listOf("Ingredient 1", "Ingredient 2"),
            steps = listOf("Step 1", "Step 2")
        )
    }

    fun setupSystemInMock(vararg inputs: String) {
        val inputString = inputs.joinToString("\n")
        System.setIn(ByteArrayInputStream(inputString.toByteArray()))
    }
}