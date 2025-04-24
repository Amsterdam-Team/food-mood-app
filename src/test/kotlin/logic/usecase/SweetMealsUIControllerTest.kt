package logic.usecase

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import logic.helpers.createMeal
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.io.PrintStream
import presentation.utils.withGreenColor
import com.google.common.truth.Truth.assertThat
import logic.exception.FoodMoodException
import presentation.uiController.SweetMealsUIController
import logic.helpers.simulateUserInput

class SweetMealsUIControllerTest {

    private lateinit var useCase: GetRandomOneSweetMealWithoutEggsUseCase
    private lateinit var controller: SweetMealsUIController
    private lateinit var outContent: ByteArrayOutputStream

    private val originalIn: InputStream = System.`in`
    private val originalOut: PrintStream = System.out

    @BeforeEach
    fun setup() {
        useCase = mockk()
        controller = SweetMealsUIController(useCase)

        outContent = ByteArrayOutputStream()
        System.setOut(PrintStream(outContent))
    }

    @AfterEach
    fun tearDown() {
        System.setIn(originalIn)
        System.setOut(originalOut)
    }

    @Test
    fun `should ask user if they like the meal and handle yes response correctly`() {
        // Given
        val sweetMeal = createMeal(
            name = "Sweet Halwa",
            description = "A delicious sweet dessert",
            preparationTime = 15,
            ingredients = listOf("sugar", "semolina", "ghee"),
            numberOfIngredients = 3,
            tags = listOf("sweet")
        )

        simulateUserInput("y")

        every { useCase.getRandomOneSweetMealWithoutEggs() } returns sweetMeal

        // When
        controller.execute()

        // Then
        val output = outContent.toString()
        assertThat(output).contains("You Are like it? y/n")
        assertThat(output).contains("Great! Enjoy your meal!")
        assertThat(output).contains(sweetMeal.toString().withGreenColor())

        verify(exactly = 1) { useCase.getRandomOneSweetMealWithoutEggs() }
    }

    @Test
    fun `should handle no response by suggesting another meal`() {
        // Given
        val firstMeal = createMeal(
            name = "Sweet Halwa",
            description = "A delicious sweet dessert",
            tags = listOf("sweet")
        )

        val secondMeal = createMeal(
            name = "Sweet Pudding",
            description = "A delicious sweet dessert",
            tags = listOf("sweet")
        )

        every { useCase.getRandomOneSweetMealWithoutEggs() } returns firstMeal andThen secondMeal

        simulateUserInput("n\ny")

        // When
        controller.execute()

        // Then
        val output = outContent.toString()

        assertThat(output).contains("Sweet Halwa")
        assertThat(output).contains("Sweet Pudding")

        verify(exactly = 2) { useCase.getRandomOneSweetMealWithoutEggs() }
    }

    @Test
    fun `should handle invalid input gracefully and try again`() {
        // Given
        val sweetMeal = createMeal(
            name = "Sweet Halwa",
            description = "A delicious sweet dessert",
            tags = listOf("sweet")
        )

        every { useCase.getRandomOneSweetMealWithoutEggs() } returns sweetMeal

        simulateUserInput("invalid\ny")

        // When
        controller.execute()

        // Then
        val output = outContent.toString()
        assertThat(output).contains("Please enter 'y' or 'n'")
        assertThat(output).contains("Great! Enjoy your meal!")

        verify(exactly = 1) { useCase.getRandomOneSweetMealWithoutEggs() }
    }

    @Test
    fun `should handle empty meal list gracefully`() {
        // Given
        every { useCase.getRandomOneSweetMealWithoutEggs() } throws FoodMoodException.Validation.MealNotFounded

        // When
        controller.execute()

        // Then
        val output = outContent.toString()
        assertThat(output).contains("Loading meals...")
        assertThat(output).contains("No meals found that match your criteria")
    }
}