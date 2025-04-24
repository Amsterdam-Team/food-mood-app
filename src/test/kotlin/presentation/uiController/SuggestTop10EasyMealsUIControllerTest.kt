package presentation.uiController

import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import logic.exception.FoodMoodException
import logic.models.Meal
import logic.usecase.SuggestTop10EasyMealsUseCase
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import presentation.uiController.helpers.FastHealthyMealsUIControllerTestFactory.UNEXPECTED_EXCEPTION_ERROR_MESSAGE
import presentation.uiController.helpers.FastHealthyMealsUIControllerTestFactory.readConsoleOutputContent
import presentation.uiController.helpers.SuggestTop10EasyMealsUIControllerTestFactory.EMPTY_DATA_EXCEPTION_ERROR_MESSAGE
import presentation.uiController.helpers.SuggestTop10EasyMealsUIControllerTestFactory.generateSampleSuggestEasyMeals
import presentation.uiController.helpers.SuggestTop10EasyMealsUIControllerTestFactory.noSpaces
import java.io.ByteArrayOutputStream
import java.io.PrintStream

class SuggestTop10EasyMealsUIControllerTest {

    private lateinit var useCase: SuggestTop10EasyMealsUseCase
    private lateinit var controller: SuggestTop10EasyMealsUIController
    private lateinit var outContent: ByteArrayOutputStream

    @BeforeEach
    fun setup() {
        useCase = mockk()
        controller = SuggestTop10EasyMealsUIController(useCase)


        // Redirect console output
        outContent = ByteArrayOutputStream()
        System.setOut(PrintStream(outContent))
    }


    @Test
    fun `execute should print formatted meals when use case succeeds`() {
        // Given
        val meals = generateSampleSuggestEasyMeals()
        every { useCase.suggestEasyMeals() } returns meals

        // When
        controller.execute()

        // Then
        val cleanedOutput = removeAnsiColors(outContent.toString()).replace("\\s+".toRegex(), "")
        assertThat(cleanedOutput.trim()).contains(noSpaces.trim())
    }

    fun removeAnsiColors(text: String): String {
        return text.replace(Regex("\u001B\\[[;\\d]*m"), "")
    }
    ///

    @Test
    fun `execute should call suggestEasyMeals exactly once`() {
        // Given
        val mockMeals: List<Meal> = listOf(mockk(), mockk())
        every { useCase.suggestEasyMeals() } returns mockMeals

        // When
        controller.execute()

        // Then
        verify(exactly = 1) { useCase.suggestEasyMeals() }
    }

    @Test
    fun `execute should print correct error message when EmptyDataException is thrown`() {
        // Given
        val exception = FoodMoodException.Validation.EmptyDataException
        every { useCase.suggestEasyMeals() } throws exception

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
        every { useCase.suggestEasyMeals() } throws unknownException

        val outContent = readConsoleOutputContent()

        // When
        controller.execute()

        // Then
        assertThat(outContent.toString()).contains(UNEXPECTED_EXCEPTION_ERROR_MESSAGE)
    }


}