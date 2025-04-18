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
    val getIraqiMealsUseCase = GetIraqiMealsUseCase(mealsRepositoryImpl)
    val iraqiMealUIController = IraqiMealUIController(getIraqiMealsUseCase)

    val getItalianMealsForLargeGroupsUseCase = GetItalianMealsForLargeGroupsUseCase(mealsRepositoryImpl)
    val italianMealUIController = ItalianMealUIController(getItalianMealsForLargeGroupsUseCase)
    val suggestTop10EasyMealsUIController =
        SuggestTop10EasyMealsUIController(SuggestTop10EasyMealsUseCase(mealsRepositoryImpl))

    val searchByIngredientsUseCase = SearchByIngredientsUseCase(mealsRepositoryImpl)
    val iLovePotatoUIController = ILovePotatoUIController(searchByIngredientsUseCase)

    val searchByCaloriesAndProteinUseCase = SearchByCaloriesAndProteinUseCase(mealsRepositoryImpl)
    val gymHelperUIController = GymHelperUIController(searchByCaloriesAndProteinUseCase)

    val handlers = mapOf(
        1 to fastHealthyMealsUiController,
        3 to iraqiMealUIController,
        4 to suggestTop10EasyMealsUIController,
        2 to getMealByNameUIController,
        5 to guessGameUIController,
        6 to SweetMealsUIController(getRandomOneSweetMealWithoutEggsUseCase),
        7 to KetoMealHelperUIController(getKetoMealsUseCase),
        8 to mealsByDateUIController,
        10 to exploreOtherCountriesUIController,
        13 to suggestMealByCalorieUI,
        14 to seafoodMealsSuccessUIController,
        15 to italianMealUIController,
        12 to iLovePotatoUIController,
        9 to gymHelperUIController

    )

    MainMenuHandler(handlers).start()

}
