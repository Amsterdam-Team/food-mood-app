package presentation.uiController

import com.google.common.truth.Truth
import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import logic.exception.FoodMoodException.Validation.EmptyDataException
import logic.helpers.*
import logic.usecase.GetItalianMealsForLargeGroupsUseCase
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class ItalianMealUIControllerTest{
    private lateinit var useCase: GetItalianMealsForLargeGroupsUseCase
    private lateinit var uiController: ItalianMealUIController

    @BeforeEach
    fun setup(){
        useCase = mockk(relaxed = true)
        uiController = ItalianMealUIController(useCase)
    }

    @Test
    fun `should execute getItalianMealsForLargeGroups once`(){
        // Given
        every { useCase.getItalianMealsForLargeGroups() } returns emptyList()

        // When
        uiController.execute()

        // Then
        verify(exactly = 1) { useCase.getItalianMealsForLargeGroups() }
    }

    @Test
    fun `should execute print correct error message when there is an exception thrown`() {
        // Given
        every { useCase.getItalianMealsForLargeGroups() } throws EmptyDataException

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
        every { useCase.getItalianMealsForLargeGroups() } returns listOf(threeInOneItalianDip())

        val outContent = readConsoleOutputContent()

        // When
        uiController.execute()

        // Then
        val actualFormatMeal = outContent.toString()
        assertThat(actualFormatMeal).contains(formattedItalianMeal())
    }
}