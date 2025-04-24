package logic.usecase


import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import logic.MealsRepository
import logic.exception.FoodMoodException.Validation.EmptyDataException
import logic.helpers.SearchByIngredientsTestFactory
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class SearchByIngredientsUseCaseTest {

    private lateinit var repository: MealsRepository
    private lateinit var useCase: SearchByIngredientsUseCase

    @BeforeEach
    fun setup() {
        repository = mockk(relaxed = true)
        useCase = SearchByIngredientsUseCase(repository)
    }

    @Test
    fun `should return meals containing the ingredient`() {
        // Given
        val ingredient = SearchByIngredientsTestFactory.LEMON
        val (mealWithIngredient, mealWithoutIngredient) =
            SearchByIngredientsTestFactory.makeMealsWithAndWithout(ingredient)

        every { repository.getAllMeals() } returns listOf(mealWithIngredient, mealWithoutIngredient)

        // When
        val result = useCase.getMealByIngredient(ingredient)

        // Then
        assertThat(result).containsExactly(mealWithIngredient)
    }

    @Test
    fun `should throw EmptyDataException when no meals contain the ingredient`() {
        // Given
        val ingredient = SearchByIngredientsTestFactory.GARLIC
        val meals = SearchByIngredientsTestFactory.makeSomeMeals()

        every { repository.getAllMeals() } returns meals

        // When & Then
        assertThrows<EmptyDataException> {
            useCase.getMealByIngredient(ingredient)
        }
    }

    @Test
    fun `should skip meals with null ingredients`() {
        // Given
        val ingredient = SearchByIngredientsTestFactory.LEMON
        val meals = SearchByIngredientsTestFactory.makeMealsWithNullAndValidIngredient(ingredient)

        every { repository.getAllMeals() } returns meals

        // When
        val result = useCase.getMealByIngredient(ingredient)

        // Then
        assertThat(result).containsExactly(meals.last()) // Only the valid one
    }
}