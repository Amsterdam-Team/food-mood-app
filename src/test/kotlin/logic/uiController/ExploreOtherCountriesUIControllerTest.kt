package logic.uiController

import com.google.common.truth.Truth
import io.mockk.every
import io.mockk.mockk
import logic.exception.FoodMoodException.Validation.NotFoundMealForThisCountry
import logic.helpers.allInTheKitchen
import logic.helpers.arribaBakedWinter
import logic.usecase.ExploreOtherCountriesByNameUseCase
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import presentation.uiController.ExploreOtherCountriesUIController

class ExploreOtherCountriesUIControllerTest {

    private lateinit var useCase: ExploreOtherCountriesByNameUseCase
    private lateinit var uiController: ExploreOtherCountriesUIController

    @BeforeEach
    fun setUp() {
        useCase = mockk(relaxed = true)
        uiController = ExploreOtherCountriesUIController(useCase)
    }

    @Test
    fun `should throw NotFoundCountryName exception when name of country not found`() {
        // Given
        val countryName = ""
        every { useCase.getRandomMealsRelatedToCountryName(countryName) } throws NotFoundMealForThisCountry

        // When & Then
        assertThrows<NotFoundMealForThisCountry> {
            uiController.exploreOtherCountriesUI()
        }
    }


    @Test
    fun `should return meals related to country name when country name is found`() {
        //given
        val countryName = "north-american"
        val allMealsRelatedToCountry = listOf(
            arribaBakedWinter(),
            allInTheKitchen()
        )
        //When
        every { useCase.getRandomMealsRelatedToCountryName(countryName) } returns allMealsRelatedToCountry

        //Then
        uiController.exploreOtherCountriesUI()

        Truth.assertThat(allMealsRelatedToCountry).containsExactlyElementsIn(
            listOf(
                arribaBakedWinter(),
                allInTheKitchen()
            )
        )
    }
}