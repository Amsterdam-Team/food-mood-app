package logic.usecase

import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import logic.MealsRepository
import logic.exception.FoodMoodException
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
    private lateinit var mealsRepository: MealsRepository
    private lateinit var getFastHealthyMealsUseCase: GetFastHealthyMealsUseCase

    @BeforeEach
    fun setup() {
        mealsRepository = mockk(relaxed = true)
        getFastHealthyMealsUseCase = GetFastHealthyMealsUseCase(mealsRepository)
    }

    @Test
    fun `getFastHealthMeals should throw empty data exception when repository given empty list`() {
        //Given
        every { mealsRepository.getAllMeals() } returns emptyList()

        //When & Then
        assertThrows<FoodMoodException.Validation.EmptyDataException> { getFastHealthyMealsUseCase.getFastHealthMeals() }
    }

    @Test
    fun `getFastHealthMeals should throw empty data exception when all meals have null nutrition`() {
        //Given
        every { mealsRepository.getAllMeals() } returns listOf(
            createMeal(nutrition = defaultNutrition, preparationTime = PREPARATION_TIME_ABOVE_TARGET),
        )

        //When & Then
        assertThrows<FoodMoodException.Validation.EmptyDataException> { getFastHealthyMealsUseCase.getFastHealthMeals() }
    }

    @Test
    fun `getFastHealthMeals should throw empty data exception when there is some null nutrition values`() {
        // Given
        val mealWithPartialNutrition = createMeal(
            nutrition = defaultNutrition.copy(totalFat = null, saturatedFat = 2.0, carbohydrates = 5.0),
            preparationTime = PREPARATION_TIME_IN_TARGET
        )
        every { mealsRepository.getAllMeals() } returns listOf(mealWithPartialNutrition)

        // When & Then
        assertThrows<FoodMoodException.Validation.EmptyDataException> { getFastHealthyMealsUseCase.getFastHealthMeals() }
    }

    @Test
    fun `getFastHealthMeals should throw empty data exception when all meals have null preparation time`() {
        //Given
        every { mealsRepository.getAllMeals() } returns listOf(
            createMeal(nutrition = defaultNutrition, preparationTime = null)
        )

        //When & Then
        assertThrows<FoodMoodException.Validation.EmptyDataException> { getFastHealthyMealsUseCase.getFastHealthMeals() }
    }

    @Test
    fun `getFastHealthMeals should throw empty data exception when all meals are valid but not healthy`() {
        //Given
        val unhealthyMeals = listOf(
            createMeal(nutrition = defaultNutrition, preparationTime = PREPARATION_TIME_IN_TARGET),
            createMeal(nutrition = defaultNutrition, preparationTime = PREPARATION_TIME_IN_TARGET)
        )
        every { mealsRepository.getAllMeals() } returns unhealthyMeals

        //When & Then
        assertThrows<FoodMoodException.Validation.EmptyDataException> { getFastHealthyMealsUseCase.getFastHealthMeals() }
    }

    @Test
    fun `getFastHealthMeals should throw empty data exception when all meals are valid but not fast`() {
        //Given
        val unhealthyMeals = listOf(
            createMeal(nutrition = defaultNutrition, preparationTime = PREPARATION_TIME_ABOVE_TARGET),
            createMeal(nutrition = defaultNutrition, preparationTime = PREPARATION_TIME_ABOVE_TARGET)
        )
        every { mealsRepository.getAllMeals() } returns unhealthyMeals

        //When & Then
        assertThrows<FoodMoodException.Validation.EmptyDataException> { getFastHealthyMealsUseCase.getFastHealthMeals() }
    }

    @Test
    fun `getFastHealthMeals should throw empty data exception when all meals have a nutrition exactly equal to average`() {
        // Given
        val meal = createMeal(
            nutrition = defaultNutrition.copy(totalFat = 30.0, saturatedFat = 5.0, carbohydrates = 10.0),
            preparationTime = PREPARATION_TIME_IN_TARGET
        )

        every { mealsRepository.getAllMeals() } returns listOf(meal, meal)

        // When & Then
        assertThrows<FoodMoodException.Validation.EmptyDataException> { getFastHealthyMealsUseCase.getFastHealthMeals() }
    }

    @Test
    fun `getFastHealthMeals should return the correct meals when all meals have valid and healthy information`() {
        // Given
        val healthyMeal1 = createMeal(
            nutrition = defaultNutrition.copy(totalFat = 30.0, saturatedFat = 5.0, carbohydrates = 10.0),
            preparationTime = PREPARATION_TIME_IN_TARGET
        )

        val healthyMeal2 = createMeal(
            nutrition = defaultNutrition.copy(totalFat = 40.0, saturatedFat = 6.0, carbohydrates = 12.0),
            preparationTime = PREPARATION_TIME_IN_TARGET
        )

        every { mealsRepository.getAllMeals() } returns listOf(healthyMeal1, healthyMeal2)

        // When & Then
        assertThat(getFastHealthyMealsUseCase.getFastHealthMeals()).containsExactly(healthyMeal1)
    }

    @Test
    fun `getFastHealthMeals should return the correct meals when all meals are healthy, but it has a preparation time out of range (1 to 15)`() {
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

        every { mealsRepository.getAllMeals() } returns mealsOutsidePrepTimeTarget

        //When & Then
        assertThat(getFastHealthyMealsUseCase.getFastHealthMeals()).containsExactly(healthyMealResult)

    }

    @Test
    fun `getFastHealthMeals should return the correct meals when there is a meal with a preparation time exactly at the edge of the preparation time range`() {
        // Given
        val mealWithMinPreparationTime = createMeal(
            nutrition = defaultNutrition.copy(totalFat = 10.0, saturatedFat = 2.0, carbohydrates = 5.0),
            preparationTime = preparationTimeRange.first
        )

        val mealWithMaxPreparationTime =
            createMeal(nutrition = defaultNutrition, preparationTime = preparationTimeRange.last)

        every { mealsRepository.getAllMeals() } returns listOf(mealWithMinPreparationTime, mealWithMaxPreparationTime)

        // When & Then
        assertThat(getFastHealthyMealsUseCase.getFastHealthMeals()).containsExactly(mealWithMinPreparationTime)
    }
}