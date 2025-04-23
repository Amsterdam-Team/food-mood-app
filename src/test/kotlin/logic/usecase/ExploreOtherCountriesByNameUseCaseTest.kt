package logic.usecase

import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import logic.MealsRepository
import logic.exception.FoodMoodException
import logic.helpers.createMeal
import logic.models.Meal
import logic.search.SearchUsingKMP
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class ExploreOtherCountriesByNameUseCaseTest {

    private lateinit var mealsRepository: MealsRepository
    private lateinit var searchUsingKMP: SearchUsingKMP
    private lateinit var exploreOtherCountriesByNameUseCase: ExploreOtherCountriesByNameUseCase

    @BeforeEach
    fun setUp() {
        mealsRepository = mockk(relaxed = true)
        searchUsingKMP = mockk(relaxed = true)
        exploreOtherCountriesByNameUseCase = ExploreOtherCountriesByNameUseCase(searchUsingKMP, mealsRepository)
    }

    @Test
    fun `should throw NotFoundCountryName exception when name of country not found`() {
        //given
        val countryName = ""
        val allMeals = emptyList<Meal>()

        every { mealsRepository.getAllMeals() } returns allMeals
        every {
            searchUsingKMP.validateTheInputInExistData(
                countryName,
                match { !it.contains(countryName) })
        } returns null

        //when && then
        assertThrows<FoodMoodException.Validation.NotFoundCountryName> {
            exploreOtherCountriesByNameUseCase.getRandomMealsRelatedToCountryName(countryName)
        }
    }

    @Test
    fun `should return random meals related to country name when country name is found`() {
        //given
        val countryName = "north-american"
        val allMeals = listOf(
            createMeal(
                name = "arriba   baked winter squash mexican style",
                tags = listOf(
                    "60-minutes-or-less",
                    "time-to-make",
                    "course",
                    "main-ingredient",
                    "cuisine",
                    "north-american"
                )
            ),
            createMeal(
                name = "all in the kitchen  chili",
                tags = listOf(
                    "30-minutes-or-less",
                    "time-to-make",
                    "course",
                    "main-ingredient",
                    "cuisine",
                    "north-american"
                )
            ),
            createMeal(
                name = "all in the kitchen  chili",
                tags = listOf(
                    "30-minutes-or-less",
                    "time-to-make",
                    "course",
                    "main-ingredient",
                    "cuisine",
                    "cairo"
                )
            ),
        )
        every { mealsRepository.getAllMeals() } returns allMeals
        every {
            searchUsingKMP.validateTheInputInExistData(
                countryName,
                match { it.contains(countryName) })
        } returns countryName
        every {
            searchUsingKMP.validateTheInputInExistData(
                countryName,
                match { !it.contains(countryName) })
        } returns null

        //when
        val mealRelatedToCountry = exploreOtherCountriesByNameUseCase.getRandomMealsRelatedToCountryName(countryName)
        print(mealRelatedToCountry)
        //then
        assertThat(mealRelatedToCountry).containsExactly(
            createMeal(
                name = "arriba   baked winter squash mexican style",
                tags = listOf(
                    "60-minutes-or-less",
                    "time-to-make",
                    "course",
                    "main-ingredient",
                    "cuisine",
                    "north-american"
                )
            ),
            createMeal(
                name = "all in the kitchen  chili",
                tags = listOf(
                    "30-minutes-or-less",
                    "time-to-make",
                    "course",
                    "main-ingredient",
                    "cuisine",
                    "north-american"
                )
            ),
        )
    }
}