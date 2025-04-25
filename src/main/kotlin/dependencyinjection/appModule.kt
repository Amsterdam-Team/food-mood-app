package dependencyinjection

import CSVFoodParser
import data.CSVFoodFileReader
import data.KetoMealsDataStore
import data.MealSuggestionDataStore
import data.MealsRepositoryImpl
import logic.MealsRepository
import logic.search.SearchUsingKMP
import org.koin.dsl.module
import presentation.console.CliConsole
import presentation.console.ConsoleIO
import presentation.uiController.ExploreOtherCountriesUIController
import presentation.uiController.FastHealthyMealsUIController
import presentation.uiController.GetMealByNameUIController
import presentation.uiController.GuessGameUIController
import presentation.uiController.GymHelperUIController
import presentation.uiController.ILovePotatoUIController
import presentation.uiController.IraqiMealUIController
import presentation.uiController.ItalianMealUIController
import presentation.uiController.KetoMealHelperUIController
import presentation.uiController.MealsByDateUIController
import presentation.uiController.SeafoodMealsUIController
import presentation.uiController.SuggestMealByCaloriesUIController
import presentation.uiController.SuggestTop10EasyMealsUIController
import presentation.uiController.SweetMealsUIController
import java.io.File

val appModule = module {
    single { File("food.csv") }
    single { CSVFoodFileReader(get()) }
    single { CSVFoodParser() }
    single<ConsoleIO> { CliConsole() }
    single<MealsRepository> { MealsRepositoryImpl(get(), get()) }
    single { MealSuggestionDataStore() }
    single { KetoMealsDataStore() }
    single { SearchUsingKMP() }
    single { ExploreOtherCountriesUIController(get()) }
    single { FastHealthyMealsUIController(get()) }
    single { GetMealByNameUIController(get()) }
    single { GuessGameUIController(get(), get()) }
    single { GymHelperUIController(get()) }
    single { ILovePotatoUIController(get()) }
    single { IraqiMealUIController(get()) }
    single { ItalianMealUIController(get()) }
    single { MealsByDateUIController(get()) }
    single { SeafoodMealsUIController(get()) }
    single { SuggestMealByCaloriesUIController(get()) }
    single { SuggestTop10EasyMealsUIController(get()) }
    single { SweetMealsUIController(get()) }
    single { KetoMealHelperUIController(get()) }

}