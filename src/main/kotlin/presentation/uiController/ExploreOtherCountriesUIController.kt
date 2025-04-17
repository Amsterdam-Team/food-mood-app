package presentation.uiController

import logic.usecase.ExploreOtherCountriesUseCase
import presentation.utils.tryToExecute
import presentation.utils.withGreenColor

class ExploreOtherCountriesUIController(
    private val exploreOtherCountriesUseCase: ExploreOtherCountriesUseCase
) : BaseUIController {

    fun exploreOtherCountriesUI() {
        println("🌍 Welcome to the International Meal Explorer!")
        print("Type a country name to discover meals related to it:\n> ")
        val countryName = readLine()?.trim() ?: ""

        println("\nSearching for meals related to \"$countryName\"...\n")

        val meals = exploreOtherCountriesUseCase.getRandomMealsRelatedToCountryName(countryName)

        println("🍽️ Found ${meals.size} meals related to \"$countryName\":")
        meals.forEach { meal ->
            println("- $meal")
        }
    }


    override fun execute() {
        tryToExecute(
            action = {
                exploreOtherCountriesUI()
            },
            onSuccess = {
                println("Success.., These are The Meals that belong to this country ..".withGreenColor())
            }
        )
    }
}