package logic.usecase

import io.mockk.every
import io.mockk.mockk
import kotlinx.datetime.LocalDate
import logic.MealsRepository
import logic.exception.FoodMoodException.ParsingException.InvalidDateFormatException
import logic.exception.FoodMoodException.Validation.NoMealsWereFoundForTheGivenDate
import logic.exception.FoodMoodException.Validation.EmptyDataException
import logic.helpers.createMeal
import logic.models.Meal
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.assertThrows
import kotlin.test.Test

class GetMealsByAddedDateUseCaseTest {
    lateinit var useCase: GetMealsByAddedDateUseCase
    lateinit var mealsRepository: MealsRepository
    val meals = listOf<Meal>(
        createMeal(submittedDate = LocalDate(2025, 9, 16), id = "50"),
        createMeal(submittedDate = LocalDate(2005, 9, 16), id = "120"),
        createMeal(submittedDate = LocalDate(2005, 9, 16), id = "13"),
    )

    @BeforeEach
    fun setup() {
        mealsRepository = mockk(relaxed = true)
        useCase = GetMealsByAddedDateUseCase(mealsRepository)
    }

    @Test
    fun `getMealsByDate should throw InvalidDateFormatException when it given invalid date format`() {
        //given
        every { mealsRepository.getAllMeals() } returns meals

        //when
        val date = "2005/9/16"

        //then
        assertThrows<InvalidDateFormatException> {
            useCase.getMealsByDate(date)
        }
    }

    @Test
    fun `getMealsByDate should throw NoMealsWereFoundForTheGivenDate exception when it given a valid date and no data associated to it`() {
        //given
        every { mealsRepository.getAllMeals() } returns meals

        //when
        val date = "2005-09-20"

        //then
        assertThrows<NoMealsWereFoundForTheGivenDate> {
            useCase.getMealsByDate(date)
        }
    }


    @Test
    fun `getMealsByDate should return all meals when it given a valid date`() {
        //given
        every { mealsRepository.getAllMeals() } returns meals

        //when
        val date = "2005-09-16"
        val result = useCase.getMealsByDate(date)

        //then
        assertFalse { result.isEmpty() }
        assertEquals(2, result.size)
    }

    @Test
    fun `getDetailedMealFromMealsData should throw EmptyDataException when it given an id that doesn't belong to any data`() {
        //given
        every { mealsRepository.getAllMeals() } returns meals

        //when
        val id = "7"

        //then
        assertThrows<EmptyDataException> {
            useCase.getDetailedMealFromMealsData(id, meals)
        }
    }


    @Test
    fun `getDetailedMealFromMealsData should return the meal that associated to the given id`() {

        //given
        every { mealsRepository.getAllMeals() } returns meals

        //when
        val id = "50"
        val requestedMeal = useCase.getDetailedMealFromMealsData(id, meals)

        //then
        assertEquals(id, requestedMeal.id)
    }

}

