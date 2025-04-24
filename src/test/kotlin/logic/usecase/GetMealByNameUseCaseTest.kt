package logic.usecase

import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import logic.MealsRepository
import logic.exception.FoodMoodException.Validation.NotFoundMealName
import logic.helpers.allInTheKitchen
import logic.helpers.arribaBakedWinter
import logic.helpers.createMeal
import logic.search.SearchUsingKMP
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class GetMealByNameUseCaseTest {

    private lateinit var useCase: GetMealByNameUseCase
    private lateinit var repository: MealsRepository
    private lateinit var searchUsingKMP: SearchUsingKMP

    @BeforeEach
    fun setUp() {
        searchUsingKMP = mockk(relaxed = true)
        repository = mockk(relaxed = true)
        useCase = GetMealByNameUseCase(searchUsingKMP, repository)
    }

    @Test
    fun `should throw NotFoundMealName when name of meal not found`() {
        //given
        val mealName = "nn"
        every { repository.getAllMeals() } returns listOf(
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
        assertThrows<NotFoundMealName> {
            useCase.getMealDetails(mealName)
        }

    }

    @Test
    fun `should return details of meal when the name of meal is found`() {
        //given
        val mealName = "bahraini sweet rice  muhammar"
        every { repository.getAllMeals() } returns listOf(
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
        val mealDetails = useCase.getMealDetails(mealName)

        //then
        assertThat(mealDetails).containsExactly(
            createMeal(name = "bahraini sweet rice  muhammar", description = "good meals"),
        )
    }
    @Test
    fun `should return details of meal when meal name is found but with missing characters from end of name`() {
        val mealName = "all in the kitchen  chi"
        val correctName = "all in the kitchen  chili"
        val meals = listOf(
            arribaBakedWinter(),
            allInTheKitchen()
        )

        every { repository.getAllMeals() } returns meals
        every {
            searchUsingKMP.validateTheInputInExistData(mealName, meals.mapNotNull { it.name })
        } returns correctName

        val mealDetails = useCase.getMealDetails(mealName)

        assertThat(mealDetails).contains(allInTheKitchen())
    }

    @Test
    fun `should return details of meal when meal name is found but with missing characters from beginning of name`() {
        val mealName = " in the kitchen  chi"
        val correctName = "all in the kitchen  chili"
        val meals = listOf(
            arribaBakedWinter(),
            allInTheKitchen()
        )

        every { repository.getAllMeals() } returns meals
        every {
            searchUsingKMP.validateTheInputInExistData(mealName, meals.mapNotNull { it.name })
        } returns correctName

        val mealDetails = useCase.getMealDetails(mealName)

        assertThat(mealDetails).contains(allInTheKitchen())
    }

    @Test
    fun `should return details of meal when meal name is found but with missing characters from middle of name`() {
        val mealName = "all in  kitchen  chi"
        val correctName = "all in the kitchen  chili"
        val meals = listOf(
            arribaBakedWinter(),
            allInTheKitchen()
        )

        every { repository.getAllMeals() } returns meals
        every {
            searchUsingKMP.validateTheInputInExistData(mealName, meals.mapNotNull { it.name })
        } returns correctName

        val mealDetails = useCase.getMealDetails(mealName)

        assertThat(mealDetails).contains(allInTheKitchen())
    }

    @Test
    fun `should return details of meal when meal name is found but the name is reversed`() {
        val mealName = "chilli kitchen the in all"
        val correctName = "all in the kitchen  chili"
        val meals = listOf(
            arribaBakedWinter(),
            allInTheKitchen()
        )

        every { repository.getAllMeals() } returns meals
        every {
            searchUsingKMP.validateTheInputInExistData(mealName, meals.mapNotNull { it.name })
        } returns correctName

        val mealDetails = useCase.getMealDetails(mealName)

        assertThat(mealDetails).contains(allInTheKitchen())
    }
}
