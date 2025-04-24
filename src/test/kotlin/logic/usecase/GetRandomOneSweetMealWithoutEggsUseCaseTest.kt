package logic.usecase

import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import logic.MealsRepository
import logic.exception.FoodMoodException.Validation.MealNotFounded
import logic.helpers.createMeal
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.Assertions.*

class GetRandomOneSweetMealWithoutEggsUseCaseTest {

    private lateinit var mealsRepository: MealsRepository

    @Test
    fun `should throw MealNotFounded when meals list is empty`() {
        mealsRepository = mockk()
        every { mealsRepository.getAllMeals() } returns emptyList()

        assertThrows<MealNotFounded> {
            val useCase = GetRandomOneSweetMealWithoutEggsUseCase(mealsRepository)
            useCase.getRandomOneSweetMealWithoutEggs()
        }
    }



    @Test
    fun `should return a sweet meal without eggs`() {
        val sweetMealNoEggs = createMeal(
            name = "Sweet Halwa",
            tags = listOf("Sweet"),
            ingredients = listOf("sugar", "semolina", "ghee")
        )

        val sweetMealWithEggs = createMeal(
            name = "Sweet Cake",
            tags = listOf("Sweet"),
            ingredients = listOf("sugar", "eggs", "flour")
        )

        val savoryMeal = createMeal(
            name = "Chicken Biryani",
            tags = listOf("Savory"),
            ingredients = listOf("chicken", "rice")
        )

        mealsRepository = mockk()
        every { mealsRepository.getAllMeals() } returns listOf(
            sweetMealNoEggs, sweetMealWithEggs, savoryMeal
        )

        val useCase = GetRandomOneSweetMealWithoutEggsUseCase(mealsRepository)

        val result = useCase.getRandomOneSweetMealWithoutEggs()
        assertThat(result.name).isEqualTo("Sweet Halwa")
    }

    @Test
    fun `should not return same meal twice`() {
        val sweetMealNoEggs1 = createMeal(
            name = "Sweet Halwa",
            tags = listOf("Sweet"),
            ingredients = listOf("sugar", "semolina", "ghee")
        )

        val sweetMealNoEggs2 = createMeal(
            name = "Sweet Pudding",
            tags = listOf("Sweet"),
            ingredients = listOf("milk", "sugar", "cornstarch")
        )

        mealsRepository = mockk()
        every { mealsRepository.getAllMeals() } returns listOf(
            sweetMealNoEggs1, sweetMealNoEggs2
        )

        val useCase = GetRandomOneSweetMealWithoutEggsUseCase(mealsRepository)

        val first = useCase.getRandomOneSweetMealWithoutEggs()
        val second = useCase.getRandomOneSweetMealWithoutEggs()

        assertThat(listOf(first.name, second.name)).containsExactlyElementsIn(
            listOf("Sweet Halwa", "Sweet Pudding")
        )
    }

    @Test
    fun `should throw MealNotFounded when all valid meals are consumed`() {
        val sweetMealNoEggs = createMeal(
            name = "Sweet Halwa",
            tags = listOf("Sweet"),
            ingredients = listOf("sugar", "semolina", "ghee")
        )

        mealsRepository = mockk()
        every { mealsRepository.getAllMeals() } returns listOf(sweetMealNoEggs)

        val useCase = GetRandomOneSweetMealWithoutEggsUseCase(mealsRepository)

        useCase.getRandomOneSweetMealWithoutEggs()

        assertThrows<MealNotFounded> {
            useCase.getRandomOneSweetMealWithoutEggs()
        }
    }
}