package presentation

import dependencyinjection.appModule
import dependencyinjection.useCaseModule
import logic.usecase.GetKetoMealsUseCase
import logic.usecase.GetRandomOneSweetMealWithoutEggsUseCase
import presentation.uiController.*
import org.koin.core.context.startKoin
import org.koin.java.KoinJavaComponent.getKoin

fun main() {
    startKoin {
        modules(
            appModule, useCaseModule
        )
    }
    val getKetoMealsUseCase: GetKetoMealsUseCase = getKoin().get()
    val getRandomOneSweetMealWithoutEggsUseCase: GetRandomOneSweetMealWithoutEggsUseCase = getKoin().get()

    val guessGameUIController: GuessGameUIController = getKoin().get()
    val fastHealthyMealsUiController: FastHealthyMealsUIController = getKoin().get()
    val seafoodMealsSuccessUIController: SeafoodMealsSuccessUIController = getKoin().get()
    val iraqiMealUIController: IraqiMealUIController = getKoin().get()
    val italianMealUIController: ItalianMealUIController = getKoin().get()
    val suggestTop10EasyMealsUIController: SuggestTop10EasyMealsUIController =
        getKoin().get()

    val getMealByNameUIController: GetMealByNameUIController = getKoin().get()
    val mealsByDateUIController: MealsByDateUIController = getKoin().get()
    val exploreOtherCountriesUIController: ExploreOtherCountriesUIController = getKoin().get()
    val suggestMealByCalorieUI: SuggestMealByCaloriesUIController = getKoin().get()

    val iLovePotatoUIController :ILovePotatoUIController= getKoin().get()
    val gymHelperUIController :GymHelperUIController= getKoin().get()

    val handlers = mapOf(
        1 to fastHealthyMealsUiController,
        2 to getMealByNameUIController,
        3 to iraqiMealUIController,
        4 to suggestTop10EasyMealsUIController,
        5 to guessGameUIController,
        6 to SweetMealsUIController(getRandomOneSweetMealWithoutEggsUseCase),
        7 to KetoMealHelperUIController(getKetoMealsUseCase),
        8 to mealsByDateUIController,
        9 to gymHelperUIController,
        10 to exploreOtherCountriesUIController,
        12 to iLovePotatoUIController,
        13 to suggestMealByCalorieUI,
        14 to seafoodMealsSuccessUIController,
        15 to italianMealUIController,
        )



    MainMenuHandler(handlers).start()

}
