package dependencyinjection
import org.koin.dsl.module
import presentation.uiController.*

val uiModule = module {

    single { GuessGameUIController(get()) }
    single { FastHealthyMealsUIController(get()) }
    single { SeafoodMealsSuccessUIController(get()) }
    single { IraqiMealUIController(get()) }
    single { ItalianMealUIController(get()) }
    single { SuggestTop10EasyMealsUIController(get()) }
    single { GetMealByNameUIController(get()) }
    single { MealsByDateUIController(get()) }
    single { ExploreOtherCountriesUIController(get()) }
    single { SuggestMealByCaloriesUIController(get()) }
    single { ILovePotatoUIController(get()) }
    single { GymHelperUIController(get()) }

    single { SweetMealsUIController(get()) }
    single { KetoMealHelperUIController(get()) }

//    // لو SweetMealsUIController و KetoMealHelperUIController مش مسجلين كـ single
//    factory { SweetMealsUIController(get()) }
//    factory { KetoMealHelperUIController(get()) }
}
