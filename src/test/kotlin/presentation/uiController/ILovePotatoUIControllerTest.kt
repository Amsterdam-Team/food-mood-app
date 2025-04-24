package presentation.uiController

import logic.helpers.SearchByIngredientsTestFactory.POTATOES
import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import logic.exception.FoodMoodException
import logic.usecase.SearchByIngredientsUseCase
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.ByteArrayOutputStream
import java.io.PrintStream
import logic.helpers.SearchByIngredientsTestFactory.makePotatoMeals
import logic.helpers.SearchByIngredientsTestFactory.makeSinglePotatoMeal

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
    fun `execute should print welcome message`() {
        // Given
        every { useCase.getMealByIngredient(POTATOES) } returns emptyList()

        // When
        controller.execute()

        // Then
        assertThat(outContent.toString()).contains("Hi, potato lover!")
    }

    @Test
    fun `execute should call use case`() {
        // Given
        every { useCase.getMealByIngredient(POTATOES) } returns emptyList()

        // When
        controller.execute()

        // Then
        verify(exactly = 1) { useCase.getMealByIngredient(POTATOES) }
    }

    @Test
    fun `execute should display meal section header`() {
        // Given
        every { useCase.getMealByIngredient(POTATOES) } returns makePotatoMeals(15)

        // When
        controller.execute()

        // Then
        assertThat(outContent.toString()).contains("Here are some meals that contain potatoes:")
    }

    @Test
    fun `execute should limit displayed meals to 10`() {
        // Given
        every { useCase.getMealByIngredient(POTATOES) } returns makePotatoMeals(15)

        // When
        controller.execute()

        // Then
        val lines = outContent.toString().lines().count { it.startsWith("Meal:") }
        assertThat(lines).isAtMost(10)
    }

    @Test
    fun `execute should show meal name`() {
        // Given
        val testMeal = makeSinglePotatoMeal()
        every { useCase.getMealByIngredient(POTATOES) } returns listOf(testMeal)

        // When
        controller.execute()

        // Then
        assertThat(outContent.toString()).contains("1. Meal: Mashed Potatoes")
    }

    @Test
    fun `execute should show meal ingredients`() {
        // Given
        val testMeal = makeSinglePotatoMeal()
        every { useCase.getMealByIngredient(POTATOES) } returns listOf(testMeal)

        // When
        controller.execute()

        // Then
        assertThat(outContent.toString()).contains("   Ingredients: [potatoes, butter, milk]")
    }

    @Test
    fun `execute should print enjoy message`() {
        // Given
        val testMeal = makeSinglePotatoMeal()
        every { useCase.getMealByIngredient(POTATOES) } returns listOf(testMeal)

        // When
        controller.execute()

        // Then
        assertThat(outContent.toString()).contains("enjoy your meal!")
    }

    @Test
    fun `execute should print loading message`() {
        // Given
        val testMeal = makeSinglePotatoMeal()
        every { useCase.getMealByIngredient(POTATOES) } returns listOf(testMeal)

        // When
        controller.execute()

        // Then
        assertThat(outContent.toString()).contains("Loading meals...")
    }

    @Test
    fun `execute should handle empty results with user-friendly message`() {
        // Given
        every { useCase.getMealByIngredient(POTATOES) } throws
                FoodMoodException.Validation.EmptyDataException

        // When
        controller.execute()

        // Then
        assertThat(outContent.toString()).contains("No meals found that match your criteria.")
    }

    @Test
    fun `execute should print error on unexpected exceptions`() {
        // Given
        every { useCase.getMealByIngredient(POTATOES) } throws
                RuntimeException("DB error")

        // When
        controller.execute()

        // Then
        assertThat(outContent.toString()).contains("An unexpected error occurred. Please try again later.")
    }

    @Test
    fun `execute should not print enjoy message on error`() {
        // Given
        every { useCase.getMealByIngredient(POTATOES) } throws
                RuntimeException("DB error")

        // When
        controller.execute()

        // Then
        assertThat(outContent.toString()).doesNotContain("enjoy your meal!")
    }

    @Test
    fun `execute should print loading message on exception`() {
        // Given
        every { useCase.getMealByIngredient(POTATOES) } throws
                RuntimeException("DB error")

        // When
        controller.execute()

        // Then
        assertThat(outContent.toString()).contains("Loading meals...")
    }

    @Test
    fun `execute should not print enjoy message on empty data exception`() {
        // Given
        every { useCase.getMealByIngredient(POTATOES) } throws
                FoodMoodException.Validation.EmptyDataException

        // When
        controller.execute()

        // Then
        assertThat(outContent.toString()).doesNotContain("enjoy your meal!")
    }

    @Test
    fun `execute should print loading message on empty data exception`() {
        // Given
        every { useCase.getMealByIngredient(POTATOES) } throws
                FoodMoodException.Validation.EmptyDataException

        // When
        controller.execute()

        // Then
        assertThat(outContent.toString()).contains("Loading meals...")
    }
}
