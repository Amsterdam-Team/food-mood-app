package presentation.uiController

import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import logic.exception.FoodMoodException
import logic.helpers.createMeal
import logic.usecase.SearchByIngredientsUseCase
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.ByteArrayOutputStream
import java.io.PrintStream

class ILovePotatoUIControllerTest {

    private lateinit var useCase: SearchByIngredientsUseCase
    private lateinit var controller: ILovePotatoUIController
    private lateinit var outContent: ByteArrayOutputStream

    @BeforeEach
    fun setup() {
        useCase = mockk()
        controller = ILovePotatoUIController(useCase)
        outContent = ByteArrayOutputStream()
        System.setOut(PrintStream(outContent))
    }

    @Test
    fun `execute should print welcome message for potato lovers`() {
        // Given
        every { useCase.getMealByIngredient("potatoes") } returns emptyList()

        // When
        controller.execute()

        // Then
        assertThat(outContent.toString()).contains("Hi, potato lover!")
    }

    @Test
    fun `execute should call use case with correct ingredient`() {
        // Given
        every { useCase.getMealByIngredient("potatoes") } returns emptyList()

        // When
        controller.execute()

        // Then
        verify(exactly = 1) { useCase.getMealByIngredient("potatoes") }
    }

    @Test
    fun `execute should display up to 10 random potato meals`() {
        // Given
        val potatoMeals = List(15) { index ->
            createMeal(
                name = "Potato Meal ${index + 1}",
                ingredients = listOf("potatoes", "other ingredient ${index + 1}")
            )
        }
        every { useCase.getMealByIngredient("potatoes") } returns potatoMeals

        // When
        controller.execute()

        // Then
        val output = outContent.toString()
        assertThat(output).contains("Here are some meals that contain potatoes:")
        assertThat(output.lines().count { it.startsWith("Meal:") }).isAtMost(10)
    }

    @Test
    fun `execute should display meal details correctly`() {
        // Given
        val testMeal = createMeal(
            name = "Mashed Potatoes",
            ingredients = listOf("potatoes", "butter", "milk")
        )
        every { useCase.getMealByIngredient("potatoes") } returns listOf(testMeal)

        // When
        controller.execute()

        // Then
        val output = outContent.toString()
        assertThat(output).contains("1. Meal: Mashed Potatoes")
        assertThat(output).contains("   Ingredients: [potatoes, butter, milk]")
    }



    @Test
    fun `execute should handle empty results gracefully`() {
        // Given
        every { useCase.getMealByIngredient("potatoes") } throws
                FoodMoodException.Validation.EmptyDataException

        // When
        controller.execute()

        // Then
        assertThat(outContent.toString()).contains("No meals found that match your criteria.")
    }



    @Test
    fun `execute should print enjoy message when meals found`() {
        // Given
        val testMeal = createMeal(name = "Test Meal", ingredients = listOf("potatoes"))
        every { useCase.getMealByIngredient("potatoes") } returns listOf(testMeal)

        // When
        controller.execute()

        // Then
        val output = outContent.toString()
        assertThat(output).contains("Loading meals...")
        assertThat(output).contains("Hi, potato lover!")
        assertThat(output).contains("enjoy your meal!")
    }

    @Test
    fun `execute should handle unexpected errors`() {
        // Given
        every { useCase.getMealByIngredient("potatoes") } throws
                RuntimeException("Database error")

        // When
        controller.execute()

        // Then
        val output = outContent.toString()
        assertThat(output).contains("Loading meals...")
        assertThat(output).contains("An unexpected error occurred. Please try again later.")
        assertThat(output).doesNotContain("enjoy your meal!")
    }

    @Test
    fun `execute should handle empty results`() {
        // Given
        every { useCase.getMealByIngredient("potatoes") } throws
                FoodMoodException.Validation.EmptyDataException

        // When
        controller.execute()

        // Then
        val output = outContent.toString()
        assertThat(output).contains("Loading meals...")
        assertThat(output).contains("No meals found that match your criteria.")
        assertThat(output).doesNotContain("enjoy your meal!")
    }
}