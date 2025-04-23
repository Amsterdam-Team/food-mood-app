package logic.usecase

import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import logic.MealsRepository
import logic.exception.FoodMoodException
import logic.helpers.SuggestTop10EasyMealsMealTestFactory
import logic.helpers.SuggestTop10EasyMealsMealTestFactory.mealWithNullIngredients
import logic.helpers.SuggestTop10EasyMealsMealTestFactory.mealWithNullPrepTime
import logic.helpers.SuggestTop10EasyMealsMealTestFactory.mealWithNullSteps
import logic.helpers.SuggestTop10EasyMealsMealTestFactory.validMeal
import logic.helpers.createMeal
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class SuggestTop10EasyMealsUseCaseTest {
    private lateinit var repository: MealsRepository
    private lateinit var useCase: SuggestTop10EasyMealsUseCase

    @BeforeEach
    fun setup() {
        repository = mockk(relaxed = true)
        useCase = SuggestTop10EasyMealsUseCase(repository)
    }

    @Test
    fun `should returns all meals when filtered list size is less than 10`() {
        //Given
        val meals = SuggestTop10EasyMealsMealTestFactory.createMultipleValidMeals(5)
        every { repository.getAllMeals() } returns meals

        //when
        val result = useCase.suggestEasyMeals()
        //then
        assertEquals(5, result.size)
     }

    @Test
    fun `should returns all meals when filtered list size is exactly 10`() {
        //Given
        val meals = SuggestTop10EasyMealsMealTestFactory.createMultipleValidMeals(10)

        every { repository.getAllMeals() } returns meals

        //when
        val result = useCase.suggestEasyMeals()

        //then
        assertEquals(result.size, 10)

    }

    @Test
    fun `should returns only 10 meals when filtered list size is greater than 10 `() {
        //Given
        val meals = SuggestTop10EasyMealsMealTestFactory.createMultipleValidMeals(20)

        every { repository.getAllMeals() } returns meals

        //when
        val result = useCase.suggestEasyMeals()

        //then
        assertEquals(10, result.size)

    }

    @Test
    fun `should throws EmptyDataException when meal list is empty`() {
        //Given
        every { repository.getAllMeals() } returns emptyList()


        //then
        assertThrows<FoodMoodException.Validation.EmptyDataException> {
            useCase.suggestEasyMeals()

        }
    }

    @Test
    fun `returns up to 10 easy meals from a valid list`() {
        //Given
        val meals = SuggestTop10EasyMealsMealTestFactory.createMultipleValidMeals(10)

        every { repository.getAllMeals() } returns meals
        // When
        val result = useCase.suggestEasyMeals()
        // Then
        assertThat(result).isNotEmpty()
        assertThat(result.size).isAtMost(10)

    }

    @Test
    fun `should return up to 10 valid easy meals and skip meals with null fields`() {
        //Given
        val invalidMeals = SuggestTop10EasyMealsMealTestFactory.createMealsWithNullFields(10)
        val validMeals = SuggestTop10EasyMealsMealTestFactory.createMultipleValidMeals(5)
        val meals = invalidMeals + validMeals

        every { repository.getAllMeals() } returns meals
        // When
        val result = useCase.suggestEasyMeals()

        // Then
        assertThat(result.size).isAtMost(10)

        result.forEach { meal ->
            assertThat(meal.preparationTime).isAtMost(30)
            assertThat(meal.numberOfIngredients).isAtMost(5)
            assertThat(meal.numberOfSteps).isAtMost(6)
        }
    }

    @Test
    fun `should only return meals with preparation time less than or equal to 30 minutes`() {
        // Given
        val meals = SuggestTop10EasyMealsMealTestFactory.createValidMealsWithPreparationTime(10, time = 25)
        every { repository.getAllMeals() } returns meals

        // When
        val result = useCase.suggestEasyMeals()

        // Then
        assertThat(result).hasSize(10)
        result.forEach { meal ->
            assertThat(meal.preparationTime).isAtMost(30)
        }
    }


    @Test
    fun `should only return meals with number of ingredients less than or equal to 5`() {
        // Given
        val meals = SuggestTop10EasyMealsMealTestFactory.createValidMealsWithIngredientsLimit(10, ingredients = 5)
        every { repository.getAllMeals() } returns meals

        // When
        val result = useCase.suggestEasyMeals()

        // Then
        assertThat(result).hasSize(10)
        result.forEach { meal ->
            assertThat(meal.numberOfIngredients).isAtMost(5)
        }
    }
    @Test
    fun `should only return meals with number of steps less than or equal to 6`() {
        // Given
        val meals = SuggestTop10EasyMealsMealTestFactory.createValidMealsWithStepsLimit(10, steps = 4)
        every { repository.getAllMeals() } returns meals

        // When
        val result = useCase.suggestEasyMeals()

        // Then
        assertThat(result).hasSize(10)
        result.forEach { meal ->
            assertThat(meal.numberOfSteps).isAtMost(6)
        }
    }
    @Test
    fun `should skip meals with null fields`() {
        val meals = listOf(
            validMeal(),
            mealWithNullPrepTime(),
            mealWithNullIngredients(),
            mealWithNullSteps(),
        )

        every { repository.getAllMeals() } returns meals

        val result = useCase.suggestEasyMeals()

        assertThat(result).containsExactly(
            createMeal(name = "Valid Meal", preparationTime = 25, numberOfIngredients = 4, numberOfSteps = 5),
        )
    }


}