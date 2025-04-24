package presentation.uiController

import logic.usecase.ExploreOtherCountriesByNameUseCase
import presentation.utils.tryToExecute
import presentation.utils.withGreenColor

class ExploreOtherCountriesUIController(
    private val exploreOtherCountriesUseCase: ExploreOtherCountriesByNameUseCase
) : BaseUIController {

    fun exploreOtherCountriesUI() {
        println("🌍 Welcome to the International Meal Explorer!")
        print("Type a country name to discover meals related to it:\n> ")
        val countryName = readLine()?.trim() ?: ""

        println("\nSearching for meals related to \"$countryName\"...\n")

        val meals = exploreOtherCountriesUseCase.getRandomMealsRelatedToCountryName(countryName)

        println("🍽️ Found ${meals.size} meals related to \"$countryName\":")
        meals.forEach { meal ->
            println("- Meal Details are :")
            meal.id?.let { println("  ID: $it") }
            meal.name?.let { println("  Name: $it") }
            meal.description?.let { println("  Description: $it") }
            meal.preparationTime?.let { println("  Preparation Time: $it") }
            meal.contributorId?.let { println(" Contributor Id: $it") }
            meal.submittedDate?.let { println("  Submitted Date: $it") }
            meal.tags?.let { println("  Tags: $it") }
            meal.nutrition?.let { println("  Nutrition: $it") }
            meal.steps?.let { println("  Steps: $it") }
            meal.numberOfSteps?.let { println("  Number Of Step: $it") }
            meal.ingredients?.let { println("  Ingredients: $it") }
            meal.numberOfIngredients?.let { println(" Number Of Ingredients: $it") }
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