package presentation

import dependencyinjection.appModule
import dependencyinjection.useCaseModule
import logic.usecase.GetRandomKetoMealUseCase
import logic.usecase.GetRandomOneSweetMealWithoutEggsUseCase
import org.koin.core.context.startKoin
import org.koin.java.KoinJavaComponent.getKoin
import presentation.uiController.*

fun main() {
    startKoin {
        modules(
            appModule, useCaseModule
        )
    }
    val getKetoMealsUseCase: GetRandomKetoMealUseCase = getKoin().get()
    val getRandomOneSweetMealWithoutEggsUseCase: GetRandomOneSweetMealWithoutEggsUseCase =
        getKoin().get()

    val guessGameUIController: GuessGameUIController = getKoin().get()
    val fastHealthyMealsUiController: FastHealthyMealsUIController = getKoin().get()
    val seafoodMealsSuccessUIController: SeafoodMealsUIController = getKoin().get()
    val iraqiMealUIController: IraqiMealUIController = getKoin().get()
    val italianMealUIController: ItalianMealUIController = getKoin().get()
    val suggestTop10EasyMealsUIController: SuggestTop10EasyMealsUIController =
        getKoin().get()

    val getMealByNameUIController: GetMealByNameUIController = getKoin().get()
    val mealsByDateUIController: MealsByDateUIController = getKoin().get()
    val exploreOtherCountriesUIController: ExploreOtherCountriesUIController = getKoin().get()
    val suggestMealByCalorieUI: SuggestMealByCaloriesUIController = getKoin().get()
    val ingredientGameUIController: IngredientGameUIController = getKoin().get()
    val iLovePotatoUIController: ILovePotatoUIController = getKoin().get()
    val gymHelperUIController: GymHelperUIController = getKoin().get()

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
        11 to ingredientGameUIController,
        13 to suggestMealByCalorieUI,
        14 to seafoodMealsSuccessUIController,
        15 to italianMealUIController,
    )



    MainMenuHandler(handlers).start()

}
