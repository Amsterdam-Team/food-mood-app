package logic.usecase

import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import logic.MealsRepository
import logic.exception.FoodMoodException
import logic.helpers.createMeal
import logic.search.SearchUsingKMP
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class GetMealByNameUseCaseTest {

    private lateinit var getMealsByNameUseCase: GetMealByNameUseCase
    private lateinit var mealRepository: MealsRepository
    private lateinit var searchUsingKMP: SearchUsingKMP

    @BeforeEach
    fun setUp() {
        searchUsingKMP = mockk(relaxed = true)
        mealRepository = mockk(relaxed = true)
        getMealsByNameUseCase = GetMealByNameUseCase(searchUsingKMP, mealRepository)
    }

    @Test
    fun `should throw NotFoundMealName when name of meal not found`() {
        //given
        val mealName = "nn"
        every { mealRepository.getAllMeals() } returns listOf(
            createMeal(name = "bahraini sweet rice  muhammar"),
            createMeal(name = "chocolate  cherry pecan biscotti")
        )
        every {
            searchUsingKMP.validateTheInputInExistData(
                mealName,
                listOf("bahraini sweet rice  muhammar", "chocolate  cherry pecan biscotti")
            )
        } returns null

        //when && throw
        assertThrows<FoodMoodException.Validation.NotFoundMealName> {
            getMealsByNameUseCase.getMealDetails(mealName)
        }

    }

    @Test
    fun `should return details of meal when the name of meal is found`() {
        //given
        val mealName = "bahraini sweet rice  muhammar"
        every { mealRepository.getAllMeals() } returns listOf(
            createMeal(name = "bahraini sweet rice  muhammar", description = "good meals"),
            createMeal(name = "chocolate  cherry pecan biscotti", description = "bad meal")
        )
        every {
            searchUsingKMP.validateTheInputInExistData(
                mealName,
                listOf("bahraini sweet rice  muhammar", "chocolate  cherry pecan biscotti")
            )
        } returns mealName

        //when
        val mealDetails = getMealsByNameUseCase.getMealDetails(mealName)

        //then
        assertThat(mealDetails).containsExactly(
            createMeal(name = "bahraini sweet rice  muhammar", description = "good meals"),
        )
    }
}