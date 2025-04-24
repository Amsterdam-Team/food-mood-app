package logic.usecase

import com.google.common.truth.Truth.assertThat
import data.MealSuggestionDataStore
import io.mockk.every
import io.mockk.mockk
import logic.MealsRepository
import logic.exception.FoodMoodException.Validation.EmptyDataException
import logic.exception.FoodMoodException.Validation.NoMoreSuggestion
import logic.helpers.createMeal
import logic.helpers.createNutrition
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.assertThrows
import kotlin.test.Test

class SuggestAMealByCaloriesUseCaseTest {
    lateinit var repository: MealsRepository
    lateinit var useCase: SuggestAMealByCaloriesUseCase
    var dataStore = MealSuggestionDataStore()

    val meals = listOf(
        createMeal(nutrition = createNutrition(calories = 1200.0)),
        createMeal(nutrition = createNutrition(calories = 800.0)),
        createMeal(nutrition = createNutrition(calories = 1000.0)),
        createMeal(nutrition = createNutrition(calories = 600.0)),
    )


    @BeforeEach
    fun setup() {
        repository = mockk(relaxed = true)
        useCase = SuggestAMealByCaloriesUseCase(repository, dataStore)
    }

    @Test
    fun `should filteredMealByCalories return meals with calories over 700`() {
        //given
        every { repository.getAllMeals() } returns meals
        //when
        val filteredMeals = useCase.filteredMealByCalories()

        //then
        assertThat(filteredMeals).hasSize(3)
    }

    @Test
    fun `should getMealRandomly return a random meal of meals with calories over 700`() {
        //given
        every { repository.getAllMeals() } returns meals

        //when
        val result = useCase.getMealRandomly()

        //then
        assertThat(meals).contains(result)

    }

    @Test
    fun `should getMealRandomly return throw EmptyDataException when filtered meal is empty`() {
        //given
        every { repository.getAllMeals() } returns emptyList()

        //when then
        assertThrows<EmptyDataException> {
            useCase.getMealRandomly()
        }
    }

    @Test
    fun `should getMealRandomly add returned meal to seen suggestions`() {
        //given
        every { repository.getAllMeals() } returns meals

        //when
        val result = useCase.getMealRandomly()

        //then
        assertThat(dataStore.checkSeenSuggestedMeal(result)).isTrue()
        assertThat(dataStore.checkTotalSeenSuggestedMeals()).isEqualTo(1)

    }

    @Test
    fun `should getMealRandomly throw NoMoreSuggestion when all filtered meals have been seen`() {
        //given
        every { repository.getAllMeals() } returns meals

        //when
        val highCalorieMeals = meals.filter { (it.nutrition?.calories ?: 0.0) > 700.0 }
        highCalorieMeals.forEach {
            dataStore.addSeenSuggestedMeal(it)
        }

        //then
        assertThat(dataStore.checkTotalSeenSuggestedMeals()).isEqualTo(highCalorieMeals.size)
        assertThrows<NoMoreSuggestion> {
            useCase.getMealRandomly()
        }
    }
}


