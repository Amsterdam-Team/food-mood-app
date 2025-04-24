package presentation.uiController

import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import logic.exception.FoodMoodException
import logic.usecase.SearchByCaloriesAndProteinUseCase
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.ByteArrayOutputStream
import java.io.PrintStream
import logic.helpers.SearchByCaloriesAndProteinUseCaseTestFactory.chicken
import logic.helpers.SearchByCaloriesAndProteinUseCaseTestFactory.steak

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
        mockUserInput("100\n50\n")
        every { useCase.getMealByCaloriesAndProtein(any(), any()) } returns emptyList()

        controller.execute()

        assertThat(outContent.toString()).contains("Hi, Welcome to your Gym Helper!")
    }

    @Test
    fun `execute should prompt for calories and protein`() {
        mockUserInput("100\n50\n")
        every { useCase.getMealByCaloriesAndProtein(any(), any()) } returns emptyList()

        controller.execute()

        assertThat(outContent.toString()).contains("Please enter your desired calories:")
        assertThat(outContent.toString()).contains("Please enter your desired protein:")
    }

    @Test
    fun `execute should call use case with correct parameters`() {
        mockUserInput("250\n30\n")
        every { useCase.getMealByCaloriesAndProtein(250, 30) } returns emptyList()

        controller.execute()

        verify(exactly = 1) { useCase.getMealByCaloriesAndProtein(250, 30) }
    }

    @Test
    fun `execute should print meals when found`() {
        mockUserInput("300\n40\n")
        val meals = listOf(
            chicken,
            steak
        )

        every { useCase.getMealByCaloriesAndProtein(any(), any()) } returns meals

        controller.execute()

        val output = outContent.toString()
        assertThat(output).contains("Meal: ${meals[0].name}, Calories: ${meals[0].nutrition?.calories}, Protein: ${meals[0].nutrition?.protein}")
        assertThat(output).contains("Meal: ${meals[1].name}, Calories: ${meals[1].nutrition?.calories}, Protein: ${meals[1].nutrition?.protein}")
        assertThat(output).contains("enjoy your meal!")
    }

    @Test
    fun `execute should handle invalid input and retry`() {
        mockUserInput("invalid\n250\nnotNumber\n30\n")
        every { useCase.getMealByCaloriesAndProtein(250, 30) } returns emptyList()

        controller.execute()

        val output = outContent.toString()
        assertThat(output).contains("Invalid input. Please enter a valid number.")
        verify(exactly = 1) { useCase.getMealByCaloriesAndProtein(250, 30) }
    }

    @Test
    fun `execute should print error message when no meals found`() {
        mockUserInput("100\n20\n")
        every { useCase.getMealByCaloriesAndProtein(any(), any()) } throws
                FoodMoodException.Validation.EmptyDataException

        controller.execute()

        assertThat(outContent.toString()).contains("No meals found that match your criteria.")
    }

    @Test
    fun `execute should print error message for unexpected exceptions`() {
        mockUserInput("150\n25\n")
        every { useCase.getMealByCaloriesAndProtein(any(), any()) } throws
                RuntimeException("Unexpected error")

        controller.execute()

        assertThat(outContent.toString()).contains("An unexpected error occurred")
    }

    private fun mockUserInput(vararg inputs: String) {
        val input = inputs.joinToString("\n")
        System.setIn(input.byteInputStream())
    }
}