package logic.usecase

import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import logic.MealsRepository
import logic.exception.FoodMoodException.Validation.EmptyDataException
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import logic.helpers.SearchByCaloriesAndProteinUseCaseTestFactory.pizza
import logic.helpers.SearchByCaloriesAndProteinUseCaseTestFactory.steak
import logic.helpers.SearchByCaloriesAndProteinUseCaseTestFactory.cucumber
import logic.helpers.SearchByCaloriesAndProteinUseCaseTestFactory.chicken
import logic.helpers.SearchByCaloriesAndProteinUseCaseTestFactory.water
import logic.helpers.SearchByCaloriesAndProteinUseCaseTestFactory.nullNutritionMeal
import logic.helpers.SearchByCaloriesAndProteinUseCaseTestFactory.nullProteinMeal
import logic.helpers.SearchByCaloriesAndProteinUseCaseTestFactory.nullCaloriesMeal
import logic.helpers.SearchByCaloriesAndProteinUseCaseTestFactory.validMeal



class SearchByCaloriesAndProteinUseCaseTest {

    private lateinit var repository: MealsRepository
    private lateinit var useCase: SearchByCaloriesAndProteinUseCase

    @BeforeEach
    fun setup() {
        repository = mockk(relaxed = true)
        useCase = SearchByCaloriesAndProteinUseCase(repository)
    }

    @Nested
    inner class HappyPathTests {
        @Test
        fun `should return exact match when calories and protein match exactly`() {
            // Given
            every { repository.getAllMeals() } returns listOf(
                pizza,
                steak,
                cucumber
            )
            // When
            val result = useCase.getMealByCaloriesAndProtein(250, 25)

            // Then
            assertThat(result).containsExactly(steak)
        }

        @ParameterizedTest
        @CsvSource(
            "251, 25",  // calories slightly higher
            "249, 25",  // calories slightly lower
            "250, 26",   // protein slightly higher
            "250, 24",  // protein slightly lower
            "251, 26",   // both slightly higher
            "249, 24"    // both slightly lower
        )
        fun `should return matches within acceptable range`(calories: Double, protein: Double) {
            // Given
            every { repository.getAllMeals() } returns listOf(
                pizza,
                steak,
                cucumber
            )
            // When
            val result = useCase.getMealByCaloriesAndProtein(calories.toInt(), protein.toInt())

            // Then
            assertThat(result).containsExactly(steak)
        }

        @Test
        fun `should return multiple matches when they fit the criteria`() {
            // Given
            every { repository.getAllMeals() } returns listOf(
                pizza,
                steak,
                chicken,
                cucumber
            )

            // When
            val result = useCase.getMealByCaloriesAndProtein(250, 25)

            // Then
            assertThat(result).containsExactly(steak, chicken)
        }
    }

    @Nested
    inner class EdgeCasesTests {
        @Test
        fun `should handle zero values correctly`() {
            // Given
            every { repository.getAllMeals() } returns listOf(water)
            // When
            val result = useCase.getMealByCaloriesAndProtein(0, 0)

            // Then
            assertThat(result).containsExactly(water)
        }


    }


        @Test
        fun `should throw EmptyDataException when no meals exist`() {
            // Given
            // When + Then
            assertThrows<EmptyDataException> {
                useCase.getMealByCaloriesAndProtein(100, 10)
            }
        }

        @Test
        fun `should throw EmptyDataException when no matching meals found`() {
            // Given
            every { repository.getAllMeals() } returns listOf(water)

            // When + Then
            assertThrows<EmptyDataException> {
                useCase.getMealByCaloriesAndProtein(100, 10)
            }
        }

        @Test
        fun `should exclude meals with null nutrition`() {
            // Given
            every { repository.getAllMeals() } returns listOf(nullNutritionMeal, validMeal)

            // When
            val result = useCase.getMealByCaloriesAndProtein(200, 20)

            // Then
            assertThat(result).containsExactly(validMeal)
        }

        @Test
        fun `should exclude meals with null calories`() {
            // Given
            every { repository.getAllMeals() } returns listOf(nullCaloriesMeal, validMeal)

            // When
            val result = useCase.getMealByCaloriesAndProtein(200, 20)

            // Then
            assertThat(result).containsExactly(validMeal)
        }

        @Test
        fun `should exclude meals with null protein`() {
            // Given
            every { repository.getAllMeals() } returns listOf(nullProteinMeal,validMeal)

            // When
            val result = useCase.getMealByCaloriesAndProtein(200, 20)

            // Then
            assertThat(result).containsExactly(validMeal)
        }
    }



