package presentation.uiController

import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import logic.exception.FoodMoodException
import logic.helpers.SeafoodMealsUIControllerTestFactory.EMPTY_DATA_EXCEPTION_ERROR_MESSAGE
import logic.helpers.SeafoodMealsUIControllerTestFactory.SHRIMP
import logic.helpers.SeafoodMealsUIControllerTestFactory.TUNA
import logic.helpers.SeafoodMealsUIControllerTestFactory.UNEXPECTED_EXCEPTION_ERROR_MESSAGE
import logic.helpers.SeafoodMealsUIControllerTestFactory.formattedShrimpMeal
import logic.helpers.SeafoodMealsUIControllerTestFactory.readConsoleOutputContent
import logic.helpers.SeafoodMealsUIControllerTestFactory.shrimpMeal
import logic.helpers.SeafoodMealsUIControllerTestFactory.tunaMeal
import logic.usecase.GetSeafoodMealsByProteinUseCase
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test

class SeafoodMealsUIControllerTest {

    private lateinit var useCase: GetSeafoodMealsByProteinUseCase
    private lateinit var controller: SeafoodMealsUIController

    @BeforeEach
    fun setUp() {
        useCase = mockk(relaxed = true)
        controller = SeafoodMealsUIController(useCase)
    }

    @Test
    fun `should execute prints correctly formatted meals when the use case succeeds`() {
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
    fun `should execute call getSeafoodMealsSortedByProtein exactly once`() {
        // Given
        every { useCase.getSeafoodMealsSortedByProtein() } returns emptyList()

        // When
        controller.execute()

        // Then
        verify(exactly = 1) { useCase.getSeafoodMealsSortedByProtein() }
    }

    @Test
    fun `should execute print multiple meals in order of protein descending`() {
        every { useCase.getSeafoodMealsSortedByProtein() } returns listOf(shrimpMeal, tunaMeal)

        val outContent = readConsoleOutputContent()

        // When
        controller.execute()

        // Then
        val outputResult = outContent.toString()
        assertThat(outputResult.indexOf(SHRIMP)).isLessThan(outputResult.indexOf(TUNA))
    }

    @Test
    fun `should execute print correct error message when there is an exception thrown`() {
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
    fun `should execute print unknown error message when unexpected exception is thrown`() {
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