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

    val handlers = mapOf(
        1 to fastHealthyMealsUiController,
        3 to iraqiMealUIController,
        4 to suggestTop10EasyMealsUIController,
        5 to guessGameUIController,
        6 to SweetMealsUIController(getRandomOneSweetMealWithoutEggsUseCase),
        7 to KetoMealHelperUIController(getKetoMealsUseCase),
        14 to seafoodMealsSuccessUIController,
        15 to italianMealUIController,

        )

    MainMenuHandler(handlers).start()

}