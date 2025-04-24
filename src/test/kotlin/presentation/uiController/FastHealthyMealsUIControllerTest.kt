package presentation.uiController

import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import logic.exception.FoodMoodException
import logic.models.Meal
import logic.usecase.GetFastHealthyMealsUseCase
import org.junit.jupiter.api.BeforeEach
import presentation.uiController.helpers.FastHealthyMealsUIControllerTestFactory.EMPTY_DATA_EXCEPTION_ERROR_MESSAGE
import presentation.uiController.helpers.FastHealthyMealsUIControllerTestFactory.UNEXPECTED_EXCEPTION_ERROR_MESSAGE
import presentation.uiController.helpers.FastHealthyMealsUIControllerTestFactory.fastPreparationMeal
import presentation.uiController.helpers.FastHealthyMealsUIControllerTestFactory.formattedFastPreparationMeal
import presentation.uiController.helpers.FastHealthyMealsUIControllerTestFactory.readConsoleOutputContent
import kotlin.test.Test

class FastHealthyMealsUIControllerTest {

    private lateinit var useCase: GetFastHealthyMealsUseCase
    private lateinit var controller: FastHealthyMealsUIController

    @BeforeEach
    fun setup() {
        useCase = mockk()
        controller = FastHealthyMealsUIController(useCase)
    }

    @Test
    fun `execute should prints formatted meals when use case succeeds`() {
        // Given
        every { useCase.getFastHealthMeals() } returns listOf(fastPreparationMeal)

        val outContent = readConsoleOutputContent()

        // When
        controller.execute()

        // Then
        val actualFormatMeal = outContent.toString()
        assertThat(actualFormatMeal).contains(formattedFastPreparationMeal)
    }

    @Test
    fun `execute should call getFastHealthMeals exactly once`() {
        // Given
        val mockMeals: List<Meal> = listOf(mockk(), mockk())

        every { useCase.getFastHealthMeals() } returns mockMeals

        // When
        controller.execute()

        // Then
        verify(exactly = 1) { useCase.getFastHealthMeals() }
    }

    @Test
    fun `execute should print correct error message when EmptyDataException is thrown`() {
        // Given
        val exception = FoodMoodException.Validation.EmptyDataException
        every { useCase.getFastHealthMeals() } throws exception

        val outContent = readConsoleOutputContent()

        // When
        controller.execute()

        // Then
        val actualErrorMessage = outContent.toString()
        assertThat(actualErrorMessage).contains(EMPTY_DATA_EXCEPTION_ERROR_MESSAGE)
    }

    @Test
    fun `execute should print unknown error message when unexpected exception is thrown`() {
        // Given
        val unknownException = RuntimeException()
        every { useCase.getFastHealthMeals() } throws unknownException

        val outContent = readConsoleOutputContent()

        // When
        controller.execute()

        // Then
        assertThat(outContent.toString()).contains(UNEXPECTED_EXCEPTION_ERROR_MESSAGE)
    }


}