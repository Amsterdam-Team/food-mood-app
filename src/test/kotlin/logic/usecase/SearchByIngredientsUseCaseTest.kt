package logic.usecase


import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import logic.MealsRepository
import logic.exception.FoodMoodException.Validation.EmptyDataException
import logic.helpers.createMeal
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class SearchByIngredientsUseCaseTest {

    private lateinit var mealsRepository: MealsRepository
    private lateinit var searchByIngredientsUseCase: SearchByIngredientsUseCase

    @BeforeEach
    fun setup() {
        mealsRepository = mockk(relaxed = true)
        searchByIngredientsUseCase = SearchByIngredientsUseCase(mealsRepository)
    }

    @Test
    fun `should return meals containing the ingredient`() {
        // Given
        val ingredient = "lemon"
        val mealWithIngredient = createMeal(
            name = "Salmon with Lemon",
            ingredients = listOf("salmon", "lemon", "salt")
        )
        val mealWithoutIngredient = createMeal(
            name = "Tuna Dish",
            ingredients = listOf("tuna", "pepper")
        )

        every { mealsRepository.getAllMeals() } returns listOf(mealWithIngredient, mealWithoutIngredient)

        // When
        val result = searchByIngredientsUseCase.getMealByIngredient(ingredient)

        // Then
        assertThat(result).containsExactly(mealWithIngredient)
    }

    @Test
    fun `should throw EmptyDataException when no meals contain the ingredient`() {
        // Given
        val ingredient = "garlic"
        val meals = listOf(
            createMeal(name = "Shrimp", ingredients = listOf("shrimp", "salt")),
            createMeal(name = "Tuna", ingredients = listOf("tuna", "pepper"))
        )

        every { mealsRepository.getAllMeals() } returns meals

        // When & Then
        assertThrows<EmptyDataException> {
            searchByIngredientsUseCase.getMealByIngredient(ingredient)
        }
    }

    @Test
    fun `should skip meals with null ingredients`() {
        // Given
        val ingredient = "lemon"
        val mealWithNullIngredients = createMeal(name = "Unknown")
        val mealWithIngredient = createMeal(name = "Lemon Dish", ingredients = listOf("lemon"))

        every { mealsRepository.getAllMeals() } returns listOf(mealWithNullIngredients, mealWithIngredient)

        // When
        val result = searchByIngredientsUseCase.getMealByIngredient(ingredient)

        // Then
        assertThat(result).containsExactly(mealWithIngredient)
    }
}