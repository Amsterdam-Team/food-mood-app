package presentation.uiController


import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import logic.exception.FoodMoodException
import logic.helpers.eggAvocadoBowl
import logic.helpers.grilledChickenSalad
import logic.helpers.zucchiniNoodlesWithPesto
import logic.usecase.GetRandomKetoMealUseCase
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import presentation.uiController.helpers.simulateUserInput
import presentation.utils.withGreenColor
import presentation.utils.withRedColor
import java.io.ByteArrayOutputStream
import java.io.PrintStream

class KetoMealHelperUIControllerTest {

    private lateinit var useCase: GetRandomKetoMealUseCase
    private lateinit var controller: KetoMealHelperUIController
    private lateinit var outContent: ByteArrayOutputStream


    @BeforeEach
    fun setup() {
        useCase = mockk()
        controller = KetoMealHelperUIController(useCase)

        outContent = ByteArrayOutputStream()
        System.setOut(PrintStream(outContent))
    }


    @Test
    fun `should ask user if they like the meal or not and handle yes response when get random keto meal and display meal name`() {
        // Given
        val ketoMeal = zucchiniNoodlesWithPesto()

        simulateUserInput("y")

        every { useCase.getRandomKetoMeal() } returns zucchiniNoodlesWithPesto()

        // When
        controller.execute()

        // Then
        val output = outContent.toString()
        assertThat(output).contains("press Y if you like it , or N if you do not like it")
        assertThat(output).contains(ketoMeal.toString().withGreenColor())

        verify(exactly = 1) { useCase.getRandomKetoMeal() }
    }

    @Test
    fun `should get another meal when user enter no`() {
        // Given
        val firstMeal = eggAvocadoBowl()
        val secondMeal = grilledChickenSalad()

        // Mock to return first meal then second meal
        every { useCase.getRandomKetoMeal() } returns firstMeal andThen secondMeal

        simulateUserInput("n\ny")

        // When
        controller.execute()

        // Then
        val output = outContent.toString()
        assertThat(output).contains("press Y if you like it , or N if you do not like it")
        assertThat(output).contains(firstMeal.name)
        assertThat(output).contains(secondMeal.toString().withGreenColor())

        verify(exactly = 2) { useCase.getRandomKetoMeal() }
    }

    @Test
    fun `should print invalid input and try again when user enter invalid input`() {
        // Given
        val ketoMeal = grilledChickenSalad()

        every { useCase.getRandomKetoMeal() } returns ketoMeal

        simulateUserInput("b\ny")

        // When
        controller.execute()

        // Then
        val output = outContent.toString()
        assertThat(output).contains("press Y if you like it , or N if you do not like it")
        assertThat(output).contains("invalid input :(".withRedColor())
        assertThat(output).contains(ketoMeal.toString().withGreenColor())

        verify(exactly = 1) { useCase.getRandomKetoMeal() }
    }

    @Test
    fun `should return no more keto meals message when get random keto meal throw empty data exception `() {
        // Given
        every { useCase.getRandomKetoMeal() } throws FoodMoodException.Validation.EmptyDataException

        // When
        controller.execute()

        // Then
        val output = outContent.toString()
        assertThat(output).contains("Loading meals...")
        assertThat(output).contains("No meals found that match your criteria")
    }

    @Test
    fun `should return no more keto meals message when get random keto meal throw no more keto meal exception`() {
        // Given
        every { useCase.getRandomKetoMeal() } throws FoodMoodException.Validation.NoMoreKetoMeals

        // When
        controller.execute()

        // Then
        val output = outContent.toString()
        assertThat(output).contains("Loading meals...")
        assertThat(output).contains("No more keto meals")
    }

}