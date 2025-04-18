package presentation

import CSVFoodParser
import data.CSVFoodFileReader
import data.MealSuggestionDataStore
import data.MealsRepositoryImpl
import logic.GetFastHealthyMealsUseCase
import logic.search.SearchUsingKMP
import logic.usecase.*
import presentation.uiController.*
import java.io.File

fun main() {
    val searchUsingKMP = SearchUsingKMP()
    val mealSuggestionDataStore = MealSuggestionDataStore()
    val csvFile = File("food.csv")
    val mealsRepositoryImpl = MealsRepositoryImpl(CSVFoodParser(CSVFoodFileReader(csvFile)))


    val getGuessPreparationTimeUseCase = GuessPreparationTimeUseCase(mealsRepositoryImpl)
    val guessGameUIController = GuessGameUIController(getGuessPreparationTimeUseCase)

    val fastHealthyMealsUseCase = GetFastHealthyMealsUseCase(mealsRepositoryImpl)
    val fastHealthyMealsUiController = FastHealthyMealsUIController(fastHealthyMealsUseCase)

    val getSeafoodMealsByProteinUseCase = GetSeafoodMealsByProteinUseCase(mealsRepositoryImpl)
    val seafoodMealsSuccessUIController = SeafoodMealsSuccessUIController(getSeafoodMealsByProteinUseCase)

    val getKetoMealsUseCase = GetKetoMealsUseCase(mealsRepositoryImpl)
    val getRandomOneSweetMealWithoutEggsUseCase = GetRandomOneSweetMealWithoutEggsUseCase(mealsRepositoryImpl)

    val suggestAMealByCaloriesUseCase = SuggestAMealByCaloriesUseCase(mealsRepositoryImpl, mealSuggestionDataStore)
    val suggestMealByCalorieUI = SuggestMealByCaloriesUIController(suggestAMealByCaloriesUseCase)

    val getMealsByAddedDateUseCase = GetMealsByAddedDateUseCase(mealsRepositoryImpl)
    val mealsByDateUIController = MealsByDateUIController(getMealsByAddedDateUseCase)
    val getMealByNamesUseCase = GetMealByNameUseCase(searchUsingKMP, mealsRepositoryImpl)
    val getMealByNameUIController = GetMealByNameUIController(getMealByNamesUseCase)

    val exploreOtherCountriesUseCase = ExploreOtherCountriesUseCase(searchUsingKMP, mealsRepositoryImpl)
    val exploreOtherCountriesUIController = ExploreOtherCountriesUIController(exploreOtherCountriesUseCase)

    val handlers = mapOf(
        1 to fastHealthyMealsUiController,
        2 to getMealByNameUIController,
        5 to guessGameUIController,
        6 to SweetMealsUIController(getRandomOneSweetMealWithoutEggsUseCase),
        7 to KetoMealHelperUIController(getKetoMealsUseCase),
        8 to mealsByDateUIController,
        10 to exploreOtherCountriesUIController,
        13 to suggestMealByCalorieUI,
        14 to seafoodMealsSuccessUIController,

        )

    MainMenuHandler(handlers).start()

}
