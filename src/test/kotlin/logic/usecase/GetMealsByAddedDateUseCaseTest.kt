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
    lateinit var repository: MealsRepository
    val meals = listOf<Meal>(
        createMeal(submittedDate = LocalDate(2025, 9, 16), id = "50"),
        createMeal(submittedDate = LocalDate(2005, 9, 16), id = "120"),
        createMeal(submittedDate = LocalDate(2005, 9, 16), id = "13"),
    )

    @BeforeEach
    fun setup() {
        repository = mockk(relaxed = true)
        useCase = GetMealsByAddedDateUseCase(repository)
    }

    @Test
    fun `should getMealsByDate throw InvalidDateFormatException when it given invalid date format`() {
        //given
        every { repository.getAllMeals() } returns meals

        //when
        val date = "2005/9/16"

        //then
        assertThrows<InvalidDateFormatException> {
            useCase.getMealsByDate(date)
        }
    }

    @Test
    fun `should getMealsByDate throw NoMealsWereFoundForTheGivenDate exception when it given a valid date and no data associated to it`() {
        //given
        every { repository.getAllMeals() } returns meals

        //when
        val date = "2005-09-20"

        //then
        assertThrows<NoMealsWereFoundForTheGivenDate> {
            useCase.getMealsByDate(date)
        }
    }


    @Test
    fun `should getMealsByDate return all meals when it given a valid date`() {
        //given
        every { repository.getAllMeals() } returns meals

        //when
        val date = "2005-09-16"
        val result = useCase.getMealsByDate(date)

        //then
        assertFalse { result.isEmpty() }
        assertEquals(2, result.size)
    }

    @Test
    fun `should getDetailedMealFromMealsData throw EmptyDataException when it given an id that doesn't belong to any data`() {
        //given
        every { repository.getAllMeals() } returns meals

        //when
        val id = "7"

        //then
        assertThrows<EmptyDataException> {
            useCase.getDetailedMealFromMealsData(id, meals)
        }
    }


    @Test
    fun `should getDetailedMealFromMealsData return the meal that associated to the given id`() {

        //given
        every { repository.getAllMeals() } returns meals

        //when
        val id = "50"
        val requestedMeal = useCase.getDetailedMealFromMealsData(id, meals)

        //then
        assertEquals(id, requestedMeal.id)
    }

}

