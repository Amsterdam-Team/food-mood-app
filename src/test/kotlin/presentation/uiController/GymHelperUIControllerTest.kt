package presentation.uiController

import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import logic.exception.FoodMoodException
import logic.helpers.createMealByProteinAndCalories
import logic.usecase.SearchByCaloriesAndProteinUseCase
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.ByteArrayOutputStream
import java.io.PrintStream

class GymHelperUIControllerTest {

    private lateinit var useCase: SearchByCaloriesAndProteinUseCase
    private lateinit var controller: GymHelperUIController
    private lateinit var outContent: ByteArrayOutputStream

    @BeforeEach
    fun setup() {
        useCase = mockk()
        controller = GymHelperUIController(useCase)
        outContent = ByteArrayOutputStream()
        System.setOut(PrintStream(outContent))
    }

    @Test
    fun `execute should print welcome message`() {
        // Given
        mockUserInput("100\n50\n")
        every { useCase.getMealByCaloriesAndProtein(any(), any()) } returns emptyList()

        // When
        controller.execute()

        // Then
        assertThat(outContent.toString()).contains("Hi, Welcome to your Gym Helper!")
    }

    @Test
    fun `execute should prompt for calories and protein`() {
        // Given
        mockUserInput("100\n50\n")
        every { useCase.getMealByCaloriesAndProtein(any(), any()) } returns emptyList()

        // When
        controller.execute()

        // Then
        assertThat(outContent.toString()).contains("Please enter your desired calories:")
        assertThat(outContent.toString()).contains("Please enter your desired protein:")
    }

    @Test
    fun `execute should call use case with correct parameters`() {
        // Given
        mockUserInput("250\n30\n")
        every { useCase.getMealByCaloriesAndProtein(250, 30) } returns emptyList()

        // When
        controller.execute()

        // Then
        verify(exactly = 1) { useCase.getMealByCaloriesAndProtein(250, 30) }
    }

    @Test
    fun `execute should print meals when found`() {
        // Given
        mockUserInput("300\n40\n")
        val meals = listOf(
            createMealByProteinAndCalories(
                name = "Chicken Breast",
                calories = 300.0,
                protein = 40.0
            ),
            createMealByProteinAndCalories(
                name = "Salmon",
                calories = 280.0,
                protein = 38.0
              )

        )

        every { useCase.getMealByCaloriesAndProtein(any(), any()) } returns meals

        // When
        controller.execute()

        // Then
        val output = outContent.toString()
        assertThat(output).contains("Meal: Chicken Breast, Calories: 300.0, Protein: 40.0")
        assertThat(output).contains("Meal: Salmon, Calories: 280.0, Protein: 38.0")
        assertThat(output).contains("enjoy your meal!")
    }

    @Test
    fun `execute should handle invalid input and retry`() {
        // Given
        mockUserInput("invalid\n250\nnotNumber\n30\n")
        every { useCase.getMealByCaloriesAndProtein(250, 30) } returns emptyList()

        // When
        controller.execute()

        // Then
        val output = outContent.toString()
        assertThat(output).contains("Invalid input. Please enter a valid number.")
        verify(exactly = 1) { useCase.getMealByCaloriesAndProtein(250, 30) }
    }

    @Test
    fun `execute should print error message when no meals found`() {
        // Given
        mockUserInput("100\n20\n")
        every { useCase.getMealByCaloriesAndProtein(any(), any()) } throws
                FoodMoodException.Validation.EmptyDataException

        // When
        controller.execute()

        // Then
        assertThat(outContent.toString()).contains("No meals found that match your criteria.")
    }

    @Test
    fun `execute should print error message for unexpected exceptions`() {
        // Given
        mockUserInput("150\n25\n")
        every { useCase.getMealByCaloriesAndProtein(any(), any()) } throws
                RuntimeException("Unexpected error")

        // When
        controller.execute()

        // Then
        assertThat(outContent.toString()).contains("An unexpected error occurred")
    }

    private fun mockUserInput(vararg inputs: String) {
        val input = inputs.joinToString("\n")
        System.setIn(input.byteInputStream())
    }
}