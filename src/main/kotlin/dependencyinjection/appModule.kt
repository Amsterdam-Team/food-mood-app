package dependencyinjection

import CSVFoodParser
import data.CSVFoodFileReader
import data.MealSuggestionDataStore
import data.MealsRepositoryImpl
import logic.MealsRepository
import logic.search.SearchUsingKMP

import org.koin.dsl.module
import presentation.uiController.*
import java.io.File

val appModule = module {
    single { File("food.csv") }
    single { CSVFoodFileReader(get()) }
    single { CSVFoodParser(get()) }
    single<MealsRepository> { MealsRepositoryImpl(get()) }
    single { MealSuggestionDataStore() }

    single { SearchUsingKMP() }

    single { ExploreOtherCountriesUIController(get()) }
    single { FastHealthyMealsUIController(get()) }
    single { GetMealByNameUIController(get()) }
    single { GuessGameUIController(get()) }
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