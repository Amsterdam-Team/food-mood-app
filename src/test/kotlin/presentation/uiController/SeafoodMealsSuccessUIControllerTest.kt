package presentation.uiController

import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import logic.exception.FoodMoodException
import logic.helpers.SeafoodMealsSuccessUIControllerTestFactory.EMPTY_DATA_EXCEPTION_ERROR_MESSAGE
import logic.helpers.SeafoodMealsSuccessUIControllerTestFactory.SHRIMP
import logic.helpers.SeafoodMealsSuccessUIControllerTestFactory.TUNA
import logic.helpers.SeafoodMealsSuccessUIControllerTestFactory.UNEXPECTED_EXCEPTION_ERROR_MESSAGE
import logic.helpers.SeafoodMealsSuccessUIControllerTestFactory.formattedShrimpMeal
import logic.helpers.SeafoodMealsSuccessUIControllerTestFactory.readConsoleOutputContent
import logic.helpers.SeafoodMealsSuccessUIControllerTestFactory.shrimpMeal
import logic.helpers.SeafoodMealsSuccessUIControllerTestFactory.tunaMeal
import logic.usecase.GetSeafoodMealsByProteinUseCase
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test

class SeafoodMealsSuccessUIControllerTest {

    private lateinit var useCase: GetSeafoodMealsByProteinUseCase
    private lateinit var controller: SeafoodMealsSuccessUIController

    @BeforeEach
    fun setUp() {
        useCase = mockk(relaxed = true)
        controller = SeafoodMealsSuccessUIController(useCase)
    }

    @Test
    fun `execute should prints correct formatted meals when use case succeeds`() {
        // Given
        every { useCase.getSeafoodMealsSortedByProtein() } returns listOf(shrimpMeal)

        val outContent = readConsoleOutputContent()

        // When
        controller.execute()

        // Then
        val actualFormatMeal = outContent.toString()
        assertThat(actualFormatMeal).contains(formattedShrimpMeal)
    }

    @Test
    fun `execute should call getSeafoodMealsSortedByProtein exactly once`() {
        // Given
        every { useCase.getSeafoodMealsSortedByProtein() } returns emptyList()

        // When
        controller.execute()

        // Then
        verify(exactly = 1) { useCase.getSeafoodMealsSortedByProtein() }
    }

    @Test
    fun `execute should print multiple meals in order of protein descending`() {
        every { useCase.getSeafoodMealsSortedByProtein() } returns listOf(shrimpMeal, tunaMeal)

        val outContent = readConsoleOutputContent()

        // When
        controller.execute()

        // Then
        val outputResult = outContent.toString()
        assertThat(outputResult.indexOf(SHRIMP)).isLessThan(outputResult.indexOf(TUNA))
    }

    @Test
    fun `execute should print correct error message when there is an exception thrown`() {
        // Given
        val exception = FoodMoodException.Validation.EmptyDataException
        every { useCase.getSeafoodMealsSortedByProtein() } throws exception

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
        every { useCase.getSeafoodMealsSortedByProtein() } throws unknownException

        val outContent = readConsoleOutputContent()

        // When
        controller.execute()

        // Then
        assertThat(outContent.toString()).contains(UNEXPECTED_EXCEPTION_ERROR_MESSAGE)
    }

}