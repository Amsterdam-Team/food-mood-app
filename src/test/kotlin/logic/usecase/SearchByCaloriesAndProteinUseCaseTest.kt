package logic.usecase

import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import logic.MealsRepository
import logic.exception.FoodMoodException.Validation.EmptyDataException
import logic.helpers.createMeal
import logic.helpers.createMealByProteinAndCalories
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class SearchByCaloriesAndProteinUseCaseTest {

    private lateinit var mealsRepository: MealsRepository
    private lateinit var useCase: SearchByCaloriesAndProteinUseCase

    @BeforeEach
    fun setup() {
        mealsRepository = mockk(relaxed = true)
        useCase = SearchByCaloriesAndProteinUseCase(mealsRepository)
    }

    @Nested
    inner class HappyPathTests {
        @Test
        fun `should return exact match when calories and protein match exactly`() {
            // Given
            val steak = createMealByProteinAndCalories("steak", 250.0, 25.0)

            every { mealsRepository.getAllMeals() } returns listOf(
                createMealByProteinAndCalories("pizza", 400.0, 50.0),
                steak,
                createMealByProteinAndCalories("cucumber", 23.0, 1.0)
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
            val steak = createMealByProteinAndCalories("steak", calories, protein)
            every { mealsRepository.getAllMeals() } returns listOf(
                createMealByProteinAndCalories("pizza", 400.0, 50.0),
                steak,
                createMealByProteinAndCalories("cucumber", 23.0, 1.0)
            )
            // When
            val result = useCase.getMealByCaloriesAndProtein(250, 25)

            // Then
            assertThat(result).containsExactly(steak)
        }

        @Test
        fun `should return multiple matches when they fit the criteria`() {
            // Given
            val steak = createMealByProteinAndCalories("steak", 251.0, 26.0)
            val chicken = createMealByProteinAndCalories("chicken", 249.0, 24.0)

            every { mealsRepository.getAllMeals() } returns listOf(
                createMealByProteinAndCalories("pizza", 400.0, 50.0),
                steak,
                chicken,
                createMealByProteinAndCalories("cucumber", 23.0, 1.0)
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
            val water = createMealByProteinAndCalories("water", 0.0, 0.0)
            every { mealsRepository.getAllMeals() } returns listOf(water)
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
            every { mealsRepository.getAllMeals() } returns listOf(createMealByProteinAndCalories("pizza", 500.0, 50.0))

            // When + Then
            assertThrows<EmptyDataException> {
                useCase.getMealByCaloriesAndProtein(100, 10)
            }
        }

        @Test
        fun `should exclude meals with null nutrition`() {
            // Given
            val nullNutritionMeal = createMeal(name = "pizza")
            val validMeal = createMealByProteinAndCalories("steak", 180.0, 18.0)
            every { mealsRepository.getAllMeals() } returns listOf(nullNutritionMeal, validMeal)

            // When
            val result = useCase.getMealByCaloriesAndProtein(180, 18)

            // Then
            assertThat(result).containsExactly(validMeal)
        }

        @Test
        fun `should exclude meals with null calories`() {
            // Given
            val nullCaloriesMeal = createMealByProteinAndCalories(
                name = "pizza",
                protein = 20.0,
                calories = null,
            )
            val validMeal = createMealByProteinAndCalories("steak", 200.0, 20.0)
            every { mealsRepository.getAllMeals() } returns listOf(nullCaloriesMeal, validMeal)

            // When
            val result = useCase.getMealByCaloriesAndProtein(200, 20)

            // Then
            assertThat(result).containsExactly(validMeal)
        }

        @Test
        fun `should exclude meals with null protein`() {
            // Given
            val nullProteinMeal = createMealByProteinAndCalories(
                name = "MissingProtein",
                calories = 200.0,
                protein = null,
            )
            val validMeal = createMealByProteinAndCalories("ValidMeal", 200.0, 20.0)
            every { mealsRepository.getAllMeals() } returns listOf(nullProteinMeal,validMeal)

            // When
            val result = useCase.getMealByCaloriesAndProtein(200, 20)

            // Then
            assertThat(result).containsExactly(validMeal)
        }
    }



