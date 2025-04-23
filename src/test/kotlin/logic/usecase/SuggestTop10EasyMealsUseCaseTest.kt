package logic.usecase

import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import logic.MealsRepository
import logic.exception.FoodMoodException
import logic.helpers.createMeal
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class SuggestTop10EasyMealsUseCaseTest {
    private lateinit var mealsRepository: MealsRepository
    private lateinit var getSuggestTop10EasyMealsUseCase: SuggestTop10EasyMealsUseCase

    @BeforeEach
    fun setup() {
        mealsRepository = mockk(relaxed = true)
        getSuggestTop10EasyMealsUseCase = SuggestTop10EasyMealsUseCase(mealsRepository)
    }

    @Test
    fun `should returns all meals when filtered list size is less than 10`() {
        //Given
        val meals = (1..5).map {
            createMeal(
                name = "Meal $it",
                preparationTime = 20,
                numberOfIngredients = 4,
                numberOfSteps = 5
            )
        }
        every { mealsRepository.getAllMeals() } returns meals

        //when
        val result = getSuggestTop10EasyMealsUseCase.suggestEasyMeals()
        //then
        assertEquals(5, result.size)
    }

    @Test
    fun `should returns all meals when filtered list size is exactly 10`() {
        //Given
        val meals = (1..10).map {
            createMeal(
                name = "Meal $it",
                preparationTime = 20,
                numberOfIngredients = 4,
                numberOfSteps = 5
            )
        }
        every { mealsRepository.getAllMeals() } returns meals

        //when
        val result = getSuggestTop10EasyMealsUseCase.suggestEasyMeals()

        //then
        assertEquals(result.size, 10)

    }

    @Test
    fun `should returns only 10 meals when filtered list size is greater than 10 `() {
        //Given
        val meals = (1..20).map {
            createMeal(
                name = "Meal $it",
                preparationTime = 20,
                numberOfIngredients = 4,
                numberOfSteps = 5
            )
        }
        every { mealsRepository.getAllMeals() } returns meals

        //when
        val result = getSuggestTop10EasyMealsUseCase.suggestEasyMeals()

        //then
        assertEquals(10, result.size)

    }

    @Test
    fun `should throws EmptyDataException when meal list is empty`() {
        //Given
        every { mealsRepository.getAllMeals() } returns emptyList()


        //then
        assertThrows<FoodMoodException.Validation.EmptyDataException> {
            getSuggestTop10EasyMealsUseCase.suggestEasyMeals()

        }

    }


    @Test
    fun `returns up to 10 easy meals from a valid list`() {
        val meals = (1..15).map {
            createMeal(
                name = "Meal $it",
                preparationTime = 20,
                numberOfIngredients = 5,
                numberOfSteps = 5
            )
        }

        every { mealsRepository.getAllMeals() } returns meals

        val result = getSuggestTop10EasyMealsUseCase.suggestEasyMeals()

        assertThat(result).isNotEmpty()
        assertThat(result.size).isAtMost(10)
        result.forEach {
            assertThat(it.preparationTime).isAtMost(30)
            assertThat(it.numberOfIngredients).isAtMost(5)
            assertThat(it.numberOfSteps).isAtMost(6)
        }
    }

    @Test
    fun `should return up to 10 valid easy meals and skip meals with null fields`() {
        val meals = (1..10).map {
            createMeal(
                name = "Meal $it",
                preparationTime = null,
                numberOfIngredients = null,
                numberOfSteps = null
            )

        }

        every { mealsRepository.getAllMeals() } returns meals

        val result = getSuggestTop10EasyMealsUseCase.suggestEasyMeals()


        assertThat(result.size).isAtMost(10)

        result.forEach { meal ->
            assertThat(meal.preparationTime).isNotNull()
            assertThat(meal.preparationTime!!).isAtMost(30)

            assertThat(meal.numberOfIngredients).isNotNull()
            assertThat(meal.numberOfIngredients!!).isAtMost(5)

            assertThat(meal.numberOfSteps).isNotNull()
            assertThat(meal.numberOfSteps!!).isAtMost(6)
        }
    }
}
