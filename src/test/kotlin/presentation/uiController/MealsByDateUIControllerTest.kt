package presentation.uiController

import io.mockk.*
import logic.exception.FoodMoodException
import logic.usecase.GetMealsByAddedDateUseCase
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import presentation.uiController.helpers.MealsByDateUIControllerTestFactory.createTestMeal
import presentation.uiController.helpers.MealsByDateUIControllerTestFactory.setupSystemInMock
import java.io.ByteArrayOutputStream
import java.io.PrintStream

class MealsByDateUIControllerTest {

    private lateinit var useCase: GetMealsByAddedDateUseCase
    private lateinit var controller: MealsByDateUIController
    private val standardOut = System.out
    private val outContent = ByteArrayOutputStream()

    @BeforeEach
    fun setup() {
        useCase = mockk(relaxed = true)
        controller = MealsByDateUIController(useCase)
        System.setOut(PrintStream(outContent))
    }

    @AfterEach
    fun tearDown() {
        System.setOut(standardOut)
    }

    @Test
    fun `execute should print formatted meals when use case succeeds`() {
        // Given
        val testDate = "2023-01-01"
        val testId = "meal123"
        val testMeal = createTestMeal()
        val meals = listOf(testMeal)
        setupSystemInMock(testDate, testId)
        every { useCase.getMealsByDate(testDate) } returns meals
        every { useCase.getDetailedMealFromMealsData(testId, meals) } returns testMeal

        // When
        controller.execute()

        // Then
        val output = outContent.toString()
        assert(output.contains("Here is all the meals founded in that date"))
        assert(output.contains("Test Meal"))
        assert(output.contains(testId))
    }

    @Test
    fun `execute should print meal details when valid meal ID is entered`() {
        // Given
        val testDate = "2023-01-01"
        val testId = "meal123"
        val testMeal = createTestMeal()
        val meals = listOf(testMeal)
        setupSystemInMock(testDate, testId)
        every { useCase.getMealsByDate(testDate) } returns meals
        every { useCase.getDetailedMealFromMealsData(testId, meals) } returns testMeal

        // When
        controller.execute()

        // Then
        val output = outContent.toString()
        assert(output.contains("Here is the full details for your meal"))
        assert(output.contains("Test Meal"))
    }

    @Test
    fun `execute should call getMealsByDate exactly once`() {
        // Given
        val testDate = "2023-01-01"
        val testId = "meal123"
        val testMeal = createTestMeal()
        val meals = listOf(testMeal)
        setupSystemInMock(testDate, testId)
        every { useCase.getMealsByDate(testDate) } returns meals
        every { useCase.getDetailedMealFromMealsData(testId, meals) } returns testMeal

        // When
        controller.execute()

        // Then
        verify(exactly = 1) { useCase.getMealsByDate(testDate) }
    }

    @Test
    fun `execute should prompt for new ID when invalid meal ID is entered`() {
        // Given
        val testDate = "2023-01-01"
        val invalidId = "invalid"
        val validId = "meal123"
        val testMeal = createTestMeal()
        val meals = listOf(testMeal)
        setupSystemInMock(testDate, invalidId, validId)
        every { useCase.getMealsByDate(testDate) } returns meals
        every {
            useCase.getDetailedMealFromMealsData(
                invalidId, meals
            )
        } throws FoodMoodException.Validation.MealNotFounded
        every { useCase.getDetailedMealFromMealsData(validId, meals) } returns testMeal

        // When
        controller.execute()

        // Then
        val output = outContent.toString()
        assert(output.contains("id is not exist"))
        assert(output.contains("Here is the full details for your meal"))
    }

    @Test
    fun `execute should print error message when NoMealsWereFoundForTheGivenDate is thrown`() {
        // Given
        val testDate = "2023-01-01"
        setupSystemInMock(testDate)

        // Mock the exception being thrown
        every { useCase.getMealsByDate(testDate) } throws FoodMoodException.Validation.NoMealsWereFoundForTheGivenDate

        // When
        controller.execute()

        // Then
        val output = outContent.toString()
        assert(output.contains("No Meals Were found for the given date"))
    }

    @Test
    fun `execute should retry with new date when first date returns no meals`() {
        // Given
        val invalidDate = "01/01/2023"
        val validDate = "02/01/2023"
        val testId = "meal123"
        val testMeal = createTestMeal()
        val meals = listOf(testMeal)

        // Set up input sequence: first invalid date, then valid date, then meal ID
        setupSystemInMock(invalidDate, validDate, testId)

        // First call with invalidDate throws exception
        every { useCase.getMealsByDate(invalidDate) } throws FoodMoodException.Validation.NoMealsWereFoundForTheGivenDate

        // Second call with validDate returns meals
        every { useCase.getMealsByDate(validDate) } returns meals

        // ID lookup returns detailed meal
        every { useCase.getDetailedMealFromMealsData(testId, meals) } returns testMeal

        // When
        controller.execute()

        // Then
        val output = outContent.toString()

        // Verify error message was shown
        assert(output.contains("No Meals Were found for the given date"))

        // Verify success message was shown
        assert(output.contains("Here is all the meals founded in that date"))

        // Verify both date requests were made
        verify(exactly = 1) { useCase.getMealsByDate(invalidDate) }
        verify(exactly = 1) { useCase.getMealsByDate(validDate) }
    }
}