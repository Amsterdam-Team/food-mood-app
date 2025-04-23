package logic.usecase

import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import logic.MealsRepository
import logic.exception.FoodMoodException
import logic.helpers.SeafoodMealTestFactory.createSeafoodMeal
import logic.helpers.SeafoodMealTestFactory.validSalmonMeal
import logic.helpers.SeafoodMealTestFactory.validShrimpMeal
import logic.helpers.createMeal
import logic.helpers.SeafoodMealTestFactory.validTunaMeal
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class GetSeafoodMealsByProteinUseCaseTest {
    private lateinit var mealsRepository: MealsRepository
    private lateinit var getSeafoodMealsByProteinUseCase: GetSeafoodMealsByProteinUseCase

    @BeforeEach
    fun setup() {
        mealsRepository = mockk(relaxed = true)
        getSeafoodMealsByProteinUseCase = GetSeafoodMealsByProteinUseCase(mealsRepository)
    }

    @Test
    fun `getSeafoodMealsSortedByProtein should return all meals when all meals have non-null name and protein`() {
        //Given
        val meals = listOf(validSalmonMeal, validTunaMeal)

        every { mealsRepository.getAllMeals() } returns meals

        //When & Then
        assertThat(getSeafoodMealsByProteinUseCase.getSeafoodMealsSortedByProtein()).containsExactlyElementsIn(meals)
    }

    @Test
    fun `getSeafoodMealsSortedByProtein should throw empty data exception when repository given empty list`() {
        //Given
        every { mealsRepository.getAllMeals() } returns emptyList()

        //When & Then
        assertThrows<FoodMoodException.Validation.EmptyDataException> { getSeafoodMealsByProteinUseCase.getSeafoodMealsSortedByProtein() }
    }

    @Test
    fun `getSeafoodMealsSortedByProtein should throw empty data exception when all meals have null nutrition`() {
        //Given
        every { mealsRepository.getAllMeals() } returns listOf(createMeal(), createMeal())

        //When & Then
        assertThrows<FoodMoodException.Validation.EmptyDataException> { getSeafoodMealsByProteinUseCase.getSeafoodMealsSortedByProtein() }
    }

    @Test
    fun `getSeafoodMealsSortedByProtein should throw empty data exception when all meals have null protein`() {
        //Given
        every { mealsRepository.getAllMeals() } returns listOf(createSeafoodMeal(), createSeafoodMeal())

        //When & Then
        assertThrows<FoodMoodException.Validation.EmptyDataException> { getSeafoodMealsByProteinUseCase.getSeafoodMealsSortedByProtein() }
    }

    @Test
    fun `getSeafoodMealsSortedByProtein should throw empty data exception when all meals have null name`() {
        //Given
        every { mealsRepository.getAllMeals() } returns listOf(createMeal(), createMeal())

        //When & Then
        assertThrows<FoodMoodException.Validation.EmptyDataException> { getSeafoodMealsByProteinUseCase.getSeafoodMealsSortedByProtein() }
    }

    @Test
    fun `getValidMeals should return only valid meals when some meals have null name or protein`() {
        // Given
        val meals = listOf(validSalmonMeal, createSeafoodMeal(protein = 10.0), createSeafoodMeal(name = "Crab"))

        every { mealsRepository.getAllMeals() } returns meals

        //When & Then
        assertThat(getSeafoodMealsByProteinUseCase.getSeafoodMealsSortedByProtein()).containsExactly(validSalmonMeal)

    }

    @Test
    fun `getSeafoodMealsSortedByProtein should return correct sorted seafood meals by descending protein when all meals are valid`() {
        // Given
        val meals = listOf(validTunaMeal, validShrimpMeal, validSalmonMeal)

        every { mealsRepository.getAllMeals() } returns meals

        // When & Then
        val sortedSeafoodMeals = listOf(validSalmonMeal, validShrimpMeal, validTunaMeal)
        assertThat(getSeafoodMealsByProteinUseCase.getSeafoodMealsSortedByProtein())
            .containsExactlyElementsIn(sortedSeafoodMeals)
            .inOrder()
    }
}