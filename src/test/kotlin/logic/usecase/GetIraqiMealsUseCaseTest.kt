package logic.usecase

import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import logic.MealsRepository
import logic.exception.FoodMoodException
import logic.helpers.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class GetIraqiMealsUseCaseTest {

    private lateinit var getIraqiMealsUseCase: GetIraqiMealsUseCase
    private lateinit var mealsRepository: MealsRepository

    @BeforeEach
    fun setup() {
        mealsRepository = mockk(relaxed = true)
        getIraqiMealsUseCase = GetIraqiMealsUseCase(mealsRepository)
    }

    @Test
    fun `should throw empty data exception when given empty list`() {
        // Given
        every { mealsRepository.getAllMeals() } returns emptyList()

        // When && Then
        assertThrows<FoodMoodException.Validation.EmptyDataException> {
            getIraqiMealsUseCase.getOnlyIraqiMeals()
        }
    }

    @Test
    fun `should return correct iraqi meals when given valid input`() {
        // Given
        every { mealsRepository.getAllMeals() } returns listOf(
            bahrainiSweetRice(),
            barmia(),
            brownedEggplantWithYogurt(),
            beefCutlets()
            )

        // When
        val result = getIraqiMealsUseCase.getOnlyIraqiMeals()

        // Then
        assertThat(result).containsExactly(
            bahrainiSweetRice(),
            barmia(),
            brownedEggplantWithYogurt(),
        )
    }

    @Test
    fun `should return correct iraqi meals without null descriptions when given other valid input`() {
        // Given
        every { mealsRepository.getAllMeals() } returns listOf(
            bahrainiSweetRice(),
            barmia().copy(description = null),
            brownedEggplantWithYogurt(),
            beefCutlets()
        )

        // When
        val result = getIraqiMealsUseCase.getOnlyIraqiMeals()

        // Then
        assertThat(result).containsExactly(
            bahrainiSweetRice(),
            brownedEggplantWithYogurt(),
        )
    }

    @Test
    fun `should return correct iraqi meals without null tags when given other valid input`() {
        // Given
        every { mealsRepository.getAllMeals() } returns listOf(
            bahrainiSweetRice().copy(tags = null),
            barmia(),
            brownedEggplantWithYogurt(),
            beefCutlets()
        )

        // When
        val result = getIraqiMealsUseCase.getOnlyIraqiMeals()

        // Then
        assertThat(result).containsExactly(
            barmia(),
            brownedEggplantWithYogurt(),
        )
    }
}