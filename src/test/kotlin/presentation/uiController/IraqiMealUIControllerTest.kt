package presentation.uiController

import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import logic.exception.FoodMoodException.Validation.EmptyDataException
import logic.helpers.*
import logic.usecase.GetIraqiMealsUseCase
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class IraqiMealUIControllerTest{

    private lateinit var useCase: GetIraqiMealsUseCase
    private lateinit var uiController: IraqiMealUIController

    @BeforeEach
    fun setup(){
        useCase = mockk(relaxed = true)
        uiController = IraqiMealUIController(useCase)
    }

    @Test
    fun `should execute getIraqiMeals once`(){
        // Given
        every { useCase.getOnlyIraqiMeals() } returns emptyList()

        // When
        uiController.execute()

        // Then
        verify(exactly = 1) { useCase.getOnlyIraqiMeals() }
    }

    @Test
    fun `should execute print correct error message when there is an exception thrown`() {
        // Given
        every { useCase.getOnlyIraqiMeals() } throws EmptyDataException

        val outContent = readConsoleOutputContent()

        // When
        uiController.execute()

        // Then
        val actualErrorMessage = outContent.toString()
        assertThat(actualErrorMessage).contains(EMPTY_DATA_EXCEPTION_ERROR_MESSAGE)
    }

    @Test
    fun `should execute prints correctly formatted meals when the use case succeeds`() {
        // Given
        every { useCase.getOnlyIraqiMeals() } returns listOf(beefCutlets())

        val outContent = readConsoleOutputContent()

        // When
        uiController.execute()

        // Then
        val actualFormatMeal = outContent.toString()
        assertThat(actualFormatMeal).contains(formattedIraqiMeal())
    }
}