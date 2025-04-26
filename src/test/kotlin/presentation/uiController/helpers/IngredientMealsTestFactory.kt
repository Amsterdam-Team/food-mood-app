package presentation.uiController.helpers

import logic.MealsRepository
import logic.helpers.createMeal
import logic.models.IngredientGameState
import logic.models.Meal
import presentation.models.IngredientGameResult

object IngredientMealsTestFactory {

    fun withMeals(meals: List<Meal>): MealsRepository {
        return object : MealsRepository {
            override fun getAllMeals(): List<Meal> = meals
        }
    }

    fun simulateUserInputs(vararg userInputs: String): AutoCloseable {
        val originalSystemIn = System.`in`
        val fakeInputStream = userInputs.joinToString("\n") { it } .plus("\n").byteInputStream()
        System.setIn(fakeInputStream)

        return AutoCloseable {
            System.setIn(originalSystemIn)
        }
    }

    fun empty(): MealsRepository = withMeals(emptyList())

    fun newGameState(): IngredientGameState = IngredientGameState()

    fun finishedGameState(): IngredientGameState =  IngredientGameState(score = 15000, correctAnswers = 15)

    fun generateMeals(count: Int): List<Meal> =
        (1..count).map {
            createMeal(
                id = "meal_$it",
                name = "Meal $it",
                ingredients = listOf("Ingredient $it")
            )
        }

    fun withNoIngredients(): MealsRepository = withMeals(
        listOf(
            createMeal(
                id = "1",
                name = "Pizza",
                ingredients = null
            )
        )
    )

    fun withInvalidIngredientsFormat(): MealsRepository = withMeals(
        listOf(
            createMeal(
                id = "1",
                name = "Pizza",
                ingredients = listOf("!!!", "@#$%")
            )
        )
    )

    fun withLessDistractors(): MealsRepository = withMeals(
        listOf(
            createMeal(id = "1", name = "MainMeal", ingredients = listOf("Tomato")),
            createMeal(id = "2", ingredients = listOf("Tomato"))
        )
    )

    fun withValidData(): MealsRepository = withMeals(
        listOf(
            createMeal(id = "1", name = "Pizza", ingredients = listOf("Cheese")),
            createMeal(id = "2", name = "Pizza2", ingredients = listOf("Tomato")),
            createMeal(id = "3", name = "Pizza3", ingredients = listOf("Basil"))
        )
    )
    fun withCorrectIngredient(): MealsRepository = withMeals(
        listOf(
            createMeal(id = "1", name = "Pizza1",ingredients = listOf("Cheese")),
            createMeal(id = "2", name = "Pizza2",ingredients = listOf("Pepper","Salt")),

            )
    )

    fun withBlankIngredient(): MealsRepository = withMeals(
        listOf(
            createMeal(id = "meal-blank",
                name = "Blank Meal",
                ingredients = listOf("", "  ")),
        ))


    fun gameFinished(score: Int = 15000, correctAnswers: Int = 15): IngredientGameResult.GameFinished {
        return IngredientGameResult.GameFinished(
            score = score,
            correctAnswers = correctAnswers
        )
    }

    fun gameInProgress(): IngredientGameResult.GameInProgress {
        return IngredientGameResult.GameInProgress(
            mealId = "meal1",
            mealName = "Pizza",
            ingredientOptions = listOf("Cheese", "Tomato", "Dough"),
            correctIngredient = "Cheese",
            score = 0,
            correctAnswers = 0
        )
    }

}