package logic.usecase

import com.google.common.truth.Truth.assertThat
import data.KetoMealsDataStore
import io.mockk.every
import io.mockk.mockk
import logic.MealsRepository
import logic.exception.FoodMoodException
import logic.helpers.createMeal
import logic.helpers.eggAvocadoBowl
import logic.helpers.grilledChickenSalad
import logic.helpers.zucchiniNoodlesWithPesto
import logic.models.Nutrition
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class GetRandomKetoMealUseCaseTest {

    private lateinit var useCase: GetRandomKetoMealUseCase
    private lateinit var mealsRepository: MealsRepository
    private lateinit var ketoMealsDataStore: KetoMealsDataStore

    @BeforeEach
    fun setUp() {
        mealsRepository = mockk(relaxed = true)
        ketoMealsDataStore = mockk(relaxed = true)

        useCase = GetRandomKetoMealUseCase(mealsRepository, ketoMealsDataStore)
    }

    @Test
    fun `should throw empty data exception when fetching random keto meal and keto meals list is empty`() {
        //Given
        every { mealsRepository.getAllMeals() } returns emptyList()
        // When && Then
        assertThrows<FoodMoodException.Validation.EmptyDataException> {

            useCase.getRandomKetoMeal()
        }

    }

    @Test
    fun `should throw no more keto meals exception when no more keto meals`() {
        //Given
        every { mealsRepository.getAllMeals() } returns listOf(eggAvocadoBowl())
        every { ketoMealsDataStore.checkTotalSeenKetoMeals() } returns 1

        // When && Then
        assertThrows<FoodMoodException.Validation.NoMoreKetoMeals> {
            useCase.getRandomKetoMeal()
        }

    }

    @Test
    fun `should return random keto meal when get random keto meal is called`() {
        //Given
        every { mealsRepository.getAllMeals() } returns listOf(
            eggAvocadoBowl(),
            zucchiniNoodlesWithPesto(),
            grilledChickenSalad()
        )
        // When

        val result = useCase.getRandomKetoMeal()
        // Then
        assertThat(result).isIn(listOf(eggAvocadoBowl(), grilledChickenSalad(), zucchiniNoodlesWithPesto()))
    }

    @Test
    fun `should return correct keto meals when meals met all constraints`() {
        //Given
        every { mealsRepository.getAllMeals() } returns listOf(
            eggAvocadoBowl(),
            zucchiniNoodlesWithPesto(),
            grilledChickenSalad()
        )
        // When

        val result = useCase.getKetoMeals()
        // Then
        assertThat(result).containsExactly(eggAvocadoBowl(), grilledChickenSalad(), zucchiniNoodlesWithPesto())
    }


    @Test
    fun `should return correct keto meals list without null descriptions when get keto meals is called`() {
        //Given
        every { mealsRepository.getAllMeals() } returns listOf(
            grilledChickenSalad(),
            eggAvocadoBowl(),
            zucchiniNoodlesWithPesto(),
            createMeal(
                name = "Keto Beef Stir Fry",
                description = null,
                nutrition = Nutrition(
                    calories = 500.0,
                    totalFat = 35.0,
                    sugar = 4.0,
                    sodium = 800.0,
                    protein = 40.0,
                    saturatedFat = 12.0,
                    carbohydrates = 5.0
                ),
            )
        )
        // When

        val result = useCase.getKetoMeals()

        // Then
        assertThat(result).containsExactly(eggAvocadoBowl(), grilledChickenSalad(), zucchiniNoodlesWithPesto())
    }

    @Test
    fun `should return correct keto meals list without null carbohydrates when get keto meals is called`() {
        //Given
        every { mealsRepository.getAllMeals() } returns listOf(
            eggAvocadoBowl(),
            grilledChickenSalad(),
            zucchiniNoodlesWithPesto(),
            createMeal(
                name = "Keto Beef Stir Fry",
                description = "keto Beef",
                nutrition = Nutrition(
                    calories = 500.0,
                    totalFat = 35.0,
                    sugar = 4.0,
                    sodium = 800.0,
                    protein = 40.0,
                    saturatedFat = 12.0,
                    carbohydrates = null
                ),
            )

        )
        // When

        val result = useCase.getKetoMeals()
        // Then
        assertThat(result).containsExactly(eggAvocadoBowl(), grilledChickenSalad(), zucchiniNoodlesWithPesto())
    }

    @Test
    fun `should return correct keto meals list without null protein when get keto meals is called`() {
        //Given
        every { mealsRepository.getAllMeals() } returns listOf(
            eggAvocadoBowl(),
            grilledChickenSalad(),
            zucchiniNoodlesWithPesto(),
            createMeal(
                name = "Keto Beef Stir Fry",
                description = "keto Beef",
                nutrition = Nutrition(
                    calories = 500.0,
                    totalFat = 35.0,
                    sugar = 4.0,
                    sodium = 800.0,
                    protein = null,
                    saturatedFat = 12.0,
                    carbohydrates = 8.0
                ),
            )

        )
        // When

        val result = useCase.getKetoMeals()
        // Then
        assertThat(result).containsExactly(eggAvocadoBowl(), grilledChickenSalad(), zucchiniNoodlesWithPesto())
    }

    @Test
    fun `should return correct keto meals list without null total fats when get keto meals is called`() {
        //Given
        every { mealsRepository.getAllMeals() } returns listOf(
            eggAvocadoBowl(),
            grilledChickenSalad(),
            zucchiniNoodlesWithPesto(),
            createMeal(
                name = "Keto Beef Stir Fry",
                description = "keto Beef",
                nutrition = Nutrition(
                    calories = 500.0,
                    totalFat = null,
                    sugar = 4.0,
                    sodium = 800.0,
                    protein = 40.0,
                    saturatedFat = 12.0,
                    carbohydrates = 8.0
                ),
            )

        )
        // When

        val result = useCase.getKetoMeals()
        // Then
        assertThat(result).containsExactly(eggAvocadoBowl(), grilledChickenSalad(), zucchiniNoodlesWithPesto())
    }


    @Test
    fun `should return another meal when call get random meal again and there are more keto meals`() {
        //Given
        every { mealsRepository.getAllMeals() } returns listOf(eggAvocadoBowl())
        every { ketoMealsDataStore.checkTotalSeenKetoMeals() } returns 0

        // When
        val result = useCase.getRandomKetoMeal()

        // Then
        assertThat(result).isIn(listOf(eggAvocadoBowl()))


    }

    @Test
    fun `should return another meal when current meal in the seen keto meals`() {
        //Given
        every { mealsRepository.getAllMeals() } returns listOf(eggAvocadoBowl(), zucchiniNoodlesWithPesto())
        every { ketoMealsDataStore.checkSeenKetoMeal(eggAvocadoBowl()) } returns true

        // When
        val result = useCase.getRandomKetoMeal()

        // Then
        assertThat(result).isIn(listOf(zucchiniNoodlesWithPesto()))


    }


}