package logic.usecase

import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import logic.MealsRepository
import logic.exception.FoodMoodException.Validation.EmptyDataException
import logic.exception.FoodMoodException.Validation.MissingPreparationTime
import logic.helpers.createMeal
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class GuessPreparationTimeUseCaseTest {
    private lateinit var useCase: GuessPreparationTimeUseCase
    private lateinit var repository: MealsRepository

    @BeforeEach
    fun setup() {
        repository = mockk()
        useCase = GuessPreparationTimeUseCase(repository)
    }

    @Test
    fun `should return meal name when get current meal is called`() {
        // Given
        val meal = createMeal(name = "pizza", preparationTime = 10)
        every { repository.getAllMeals() } returns listOf(meal)

        // When
        val result = useCase.getCurrentMeal()

        // Then
        assertThat(result).isEqualTo("pizza")
    }

    @Test
    fun `should return correct when guess equals preparation time`() {
        // Given
        val meal = createMeal(name = "any", preparationTime = 12)
        every { repository.getAllMeals() } returns listOf(meal)

        // When
        val result = useCase.checkGuess(12)

        // Then
        assertThat(result).isInstanceOf(GuessPreparationTimeUseCase.ComparisonResult.Correct::class.java)
    }

    @Test
    fun `should return that you're very close when guess is off by one`() {
        // Given
        val meal = createMeal(name = "any", preparationTime = 10)
        every { repository.getAllMeals() } returns listOf(meal)

        // When
        val result = useCase.checkGuess(9)

        // Then
        assertThat(result).isInstanceOf(GuessPreparationTimeUseCase.ComparisonResult.Close::class.java)
    }

    @Test
    fun `should return that you're too close when guess is off by two`() {
        // Given
        val meal = createMeal(name = "any", preparationTime = 10)
        every { repository.getAllMeals() } returns listOf(meal)

        // When
        val result = useCase.checkGuess(12)

        // Then
        assertThat(result).isInstanceOf(GuessPreparationTimeUseCase.ComparisonResult.Close::class.java)
    }

    @Test
    fun `should return too high when guess is more than 2 over actual`() {
        // Given
        val meal = createMeal(name = "any", preparationTime = 10)
        every { repository.getAllMeals() } returns listOf(meal)

        // When
        val result = useCase.checkGuess(13)

        // Then
        assertThat(result).isInstanceOf(GuessPreparationTimeUseCase.ComparisonResult.TooHigh::class.java)
    }

    @Test
    fun `should return too low when guess is more than 2 below actual`() {
        // Given
        val meal = createMeal(name = "any", preparationTime = 10)
        every { repository.getAllMeals() } returns listOf(meal)

        // When
        val result = useCase.checkGuess(7)

        // Then
        assertThat(result).isInstanceOf(GuessPreparationTimeUseCase.ComparisonResult.TooLow::class.java)
    }

    @Test
    fun `should throw empty data exception when no valid meals exist`() {
        // Given
        val invalidMeal = createMeal(name = null, preparationTime = null)
        every { repository.getAllMeals() } returns listOf(invalidMeal)

        // When & Then
        assertThrows<EmptyDataException> {
            useCase.getCurrentMeal()
        }
    }

    @Test
    fun `should throw missing preparation time when meal has null time`() {
        // Given
        val meal = createMeal(name = "any", preparationTime = null)
        every { repository.getAllMeals() } returns listOf(meal)

        // When & Then
        assertThrows<MissingPreparationTime> {
            useCase.checkGuess(5)
        }
    }
}