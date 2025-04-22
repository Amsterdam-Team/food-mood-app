package presentation

import dependencyinjection.appModule
import dependencyinjection.uiModule
import dependencyinjection.useCaseModule
import presentation.uiController.*
import org.koin.core.context.startKoin
import org.koin.java.KoinJavaComponent.getKoin

fun main() {
    startKoin {
        modules(
            appModule, useCaseModule, uiModule
        )
    }

    val handlers = mapOf(
        1 to getKoin().get<FastHealthyMealsUIController>(),
        2 to getKoin().get<GetMealByNameUIController>(),
        3 to getKoin().get<IraqiMealUIController>(),
        4 to getKoin().get<SuggestTop10EasyMealsUIController>(),
        5 to getKoin().get<GuessGameUIController>(),
        6 to getKoin().get<SweetMealsUIController>(),
        7 to getKoin().get<KetoMealHelperUIController>(),
        8 to getKoin().get<MealsByDateUIController>(),
        9 to getKoin().get<GymHelperUIController>(),
        10 to getKoin().get<ExploreOtherCountriesUIController>(),
        12 to getKoin().get<ILovePotatoUIController>(),
        13 to getKoin().get<SuggestMealByCaloriesUIController>(),
        14 to getKoin().get<SeafoodMealsSuccessUIController>(),
        15 to getKoin().get<ItalianMealUIController>()
        )



    MainMenuHandler(handlers).start()

}

