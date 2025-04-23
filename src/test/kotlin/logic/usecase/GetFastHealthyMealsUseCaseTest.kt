package logic.usecase

import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import logic.MealsRepository
import logic.exception.FoodMoodException.*
import logic.helpers.FastHealthyMealTestFactory.PREPARATION_TIME_ABOVE_TARGET
import logic.helpers.FastHealthyMealTestFactory.PREPARATION_TIME_IN_TARGET
import logic.helpers.FastHealthyMealTestFactory.defaultNutrition
import logic.helpers.FastHealthyMealTestFactory.mealWithAboveTimeTarget
import logic.helpers.FastHealthyMealTestFactory.mealWithNegativePreparationTime
import logic.helpers.FastHealthyMealTestFactory.mealWithNotHealthyNutrition
import logic.helpers.FastHealthyMealTestFactory.mealWithZeroPreparationTime
import logic.helpers.FastHealthyMealTestFactory.preparationTimeRange
import logic.helpers.createMeal
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class GetFastHealthyMealsUseCaseTest {
    private lateinit var repository: MealsRepository
    private lateinit var useCase: GetFastHealthyMealsUseCase

    @BeforeEach
    fun setup() {
        repository = mockk(relaxed = true)
        useCase = GetFastHealthyMealsUseCase(repository)
    }

    @Test
    fun `should throw empty data exception when repository given empty list`() {
        //Given
        every { repository.getAllMeals() } returns emptyList()

        //When & Then
        assertThrows<EmptyDataException> { useCase.getFastHealthMeals() }
    }

    @Test
    fun `should throw empty data exception when all meals have null nutrition`() {
        //Given
        every { repository.getAllMeals() } returns listOf(
            createMeal(nutrition = defaultNutrition, preparationTime = PREPARATION_TIME_ABOVE_TARGET),
        )

        //When & Then
        assertThrows<EmptyDataException> { useCase.getFastHealthMeals() }
    }

    @Test
    fun `should throw empty data exception when there is some null nutrition values`() {
        // Given
        val mealWithPartialNutrition = createMeal(
            nutrition = defaultNutrition.copy(totalFat = null, saturatedFat = 2.0, carbohydrates = 5.0),
            preparationTime = PREPARATION_TIME_IN_TARGET
        )
        every { repository.getAllMeals() } returns listOf(mealWithPartialNutrition)

        // When & Then
        assertThrows<EmptyDataException> { useCase.getFastHealthMeals() }
    }

    @Test
    fun `should throw empty data exception when all meals have null preparation time`() {
        //Given
        every { repository.getAllMeals() } returns listOf(
            createMeal(nutrition = defaultNutrition, preparationTime = null)
        )

        //When & Then
        assertThrows<EmptyDataException> { useCase.getFastHealthMeals() }
    }

    @Test
    fun `should throw empty data exception when all meals are valid but not healthy`() {
        //Given
        val unhealthyMeals = listOf(
            createMeal(nutrition = defaultNutrition, preparationTime = PREPARATION_TIME_IN_TARGET),
            createMeal(nutrition = defaultNutrition, preparationTime = PREPARATION_TIME_IN_TARGET)
        )
        every { repository.getAllMeals() } returns unhealthyMeals

        //When & Then
        assertThrows<EmptyDataException> { useCase.getFastHealthMeals() }
    }

    @Test
    fun `should throw empty data exception when all meals are valid but not fast`() {
        //Given
        val unhealthyMeals = listOf(
            createMeal(nutrition = defaultNutrition, preparationTime = PREPARATION_TIME_ABOVE_TARGET),
            createMeal(nutrition = defaultNutrition, preparationTime = PREPARATION_TIME_ABOVE_TARGET)
        )
        every { repository.getAllMeals() } returns unhealthyMeals

        //When & Then
        assertThrows<EmptyDataException> { useCase.getFastHealthMeals() }
    }

    @Test
    fun `should throw empty data exception when all meals have a nutrition exactly equal to average`() {
        // Given
        val meal = createMeal(
            nutrition = defaultNutrition.copy(totalFat = 30.0, saturatedFat = 5.0, carbohydrates = 10.0),
            preparationTime = PREPARATION_TIME_IN_TARGET
        )

        every { repository.getAllMeals() } returns listOf(meal, meal)

        // When & Then
        assertThrows<EmptyDataException> { useCase.getFastHealthMeals() }
    }

    @Test
    fun `should return the correct meals when all meals have valid and healthy information`() {
        // Given
        val healthyMeal1 = createMeal(
            nutrition = defaultNutrition.copy(totalFat = 30.0, saturatedFat = 5.0, carbohydrates = 10.0),
            preparationTime = PREPARATION_TIME_IN_TARGET
        )

        val healthyMeal2 = createMeal(
            nutrition = defaultNutrition.copy(totalFat = 40.0, saturatedFat = 6.0, carbohydrates = 12.0),
            preparationTime = PREPARATION_TIME_IN_TARGET
        )

        every { repository.getAllMeals() } returns listOf(healthyMeal1, healthyMeal2)

        // When & Then
        assertThat(useCase.getFastHealthMeals()).containsExactly(healthyMeal1)
    }

    @Test
    fun `should return the correct meals when all meals are healthy, but it has a preparation time out of range (1 to 15)`() {
        //Given
        val healthyMealResult = createMeal(
            nutrition = defaultNutrition.copy(totalFat = 40.0, saturatedFat = 8.0, carbohydrates = 15.0),
            preparationTime = PREPARATION_TIME_IN_TARGET
        )
        val mealsOutsidePrepTimeTarget = listOf(
            mealWithAboveTimeTarget,
            mealWithNotHealthyNutrition,
            mealWithNegativePreparationTime,
            mealWithZeroPreparationTime,
            healthyMealResult,
        )

        every { repository.getAllMeals() } returns mealsOutsidePrepTimeTarget

        //When & Then
        assertThat(useCase.getFastHealthMeals()).containsExactly(healthyMealResult)

    }

    @Test
    fun `should return the correct meals when there is a meal with a preparation time exactly at the edge of the preparation time range`() {
        // Given
        val mealWithMinPreparationTime = createMeal(
            nutrition = defaultNutrition.copy(totalFat = 10.0, saturatedFat = 2.0, carbohydrates = 5.0),
            preparationTime = preparationTimeRange.first
        )

        val mealWithMaxPreparationTime =
            createMeal(nutrition = defaultNutrition, preparationTime = preparationTimeRange.last)

        every { repository.getAllMeals() } returns listOf(mealWithMinPreparationTime, mealWithMaxPreparationTime)

        // When & Then
        assertThat(useCase.getFastHealthMeals()).containsExactly(mealWithMinPreparationTime)
    }
}