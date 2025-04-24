package logic.usecase

import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import logic.MealsRepository
import logic.exception.FoodMoodException.Validation.NotFoundMealForThisCountry
import logic.helpers.allInChili
import logic.helpers.allInTheKitchen
import logic.helpers.arribaBakedWinter
import logic.search.SearchUsingKMP
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows


class ExploreOtherCountriesByNameUseCaseTest {

    private lateinit var repository: MealsRepository
    private lateinit var searchUsingKMP: SearchUsingKMP
    private lateinit var useCase: ExploreOtherCountriesByNameUseCase

    @BeforeEach
    fun setUp() {
        repository = mockk(relaxed = true)
        searchUsingKMP = mockk(relaxed = true)
        useCase = ExploreOtherCountriesByNameUseCase(searchUsingKMP, repository)
    }

    @Test
    fun `should throw NotFoundCountryName exception when name of country not found`() {
        //given
        val countryName = ""

        every { repository.getAllMeals() } returns emptyList()
        every {
            searchUsingKMP.validateTheInputInExistData(
                countryName,
                match { !it.contains(countryName) })
        } returns null

        //when && then
        assertThrows<NotFoundMealForThisCountry> {
            useCase.getRandomMealsRelatedToCountryName(countryName)
        }
    }

    @Test
    fun `should return meals related to country name when country name is found`() {
        //given
        val countryName = "north-american"
        val allMeals = listOf(
            arribaBakedWinter(),
            allInTheKitchen(),
            allInChili()
        )
        every { repository.getAllMeals() } returns allMeals
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
        val mealRelatedToCountry = useCase.getRandomMealsRelatedToCountryName(countryName)

        assertThat(mealRelatedToCountry).containsExactlyElementsIn(
            listOf(
                allInTheKitchen(),
                arribaBakedWinter(),
            )
        )

    }

    @Test
    fun `should return meal related to country name when country name is found but with missing characters from end of name`() {
        //given
        val countryName = "north-americ"
        val correctName = "north-american"
        val meal = arribaBakedWinter()

        every { repository.getAllMeals() } returns listOf(meal)
        every {
            searchUsingKMP.validateTheInputInExistData(
                countryName,
                meal.tags
            )
        } returns correctName

        //when
        val mealRelatedToCountry = useCase.getRandomMealsRelatedToCountryName(countryName)

        assertThat(mealRelatedToCountry).contains(
            arribaBakedWinter(),
        )

    }

    @Test
    fun `should return meal related to country name when country name is found but with missing characters from start of name`() {
        //given
        val countryName = "rth-american"
        val correctName = "north-american"
        val meal = arribaBakedWinter()

        every { repository.getAllMeals() } returns listOf(meal)
        every {
            searchUsingKMP.validateTheInputInExistData(
                countryName,
                meal.tags
            )
        } returns correctName

        //when
        val mealRelatedToCountry = useCase.getRandomMealsRelatedToCountryName(countryName)

        assertThat(mealRelatedToCountry).contains(
            arribaBakedWinter(),
        )

    }

    @Test
    fun `should return meal related to country name when country name is found but with missing characters in the middle of name`() {
        //given
        val countryName = "north merican"
        val correctName = "north-american"
        val meal = arribaBakedWinter()

        every { repository.getAllMeals() } returns listOf(meal)
        every {
            searchUsingKMP.validateTheInputInExistData(
                countryName,
                meal.tags
            )
        } returns correctName

        //when
        val mealRelatedToCountry = useCase.getRandomMealsRelatedToCountryName(countryName)

        assertThat(mealRelatedToCountry).contains(
            arribaBakedWinter(),
        )

    }


    @Test
    fun `should return meal related to country name when country name is found but the name is reversed`() {
        //given
        val countryName = "americ-north"
        val correctName = "north-american"
        val meal = arribaBakedWinter()

        every { repository.getAllMeals() } returns listOf(meal)
        every {
            searchUsingKMP.validateTheInputInExistData(
                countryName,
                meal.tags
            )
        } returns correctName

        //when
        val mealRelatedToCountry = useCase.getRandomMealsRelatedToCountryName(countryName)

        assertThat(mealRelatedToCountry).contains(
            arribaBakedWinter()
        )

    }

}