package logic.usecase

import logic.MealsRepository
import logic.exception.FoodMoodException
import logic.models.Meal
import logic.search.SearchUsingKMP
import org.example.presentation.utils.getRandomElements

class ExploreOtherCountriesUseCase (
    private val searchUsingKMP: SearchUsingKMP,
    private val mealsRepository: MealsRepository
) {

    fun getRandomMealsRelatedToCountryName(countryName: String): List<Meal> {
        val allMeals = mealsRepository.getAllMeals()

        val mealsRelatedToCountry = allMeals.filter {
            searchUsingKMP.validateTheInputInExistData(countryName, it.tags) != null
        }

        if (mealsRelatedToCountry.isEmpty())
            throw FoodMoodException.Validation.NotFoundCountryName

        return mealsRelatedToCountry.getRandomElements(20)
    }
}