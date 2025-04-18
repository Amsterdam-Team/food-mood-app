package presentation

import data.MealsRepositoryImpl
import logic.GetFastHealthyMealsUseCase
import logic.usecase.GetKetoMealsUseCase
import logic.usecase.GetSeafoodMealsByProteinUseCase
import logic.usecase.GuessPreparationTimeUseCase
import logic.usecase.SuggestTop10EasyMealsUseCase
import presentation.uiController.*
import java.io.File

fun main() {
    val meals = MealsRepositoryImpl(File("food.csv")).getAllMeals()
    val csvFile = File("food.csv")
    val mealsRepositoryImpl = MealsRepositoryImpl(csvFile)

    val getGuessPreparationTimeUseCase = GuessPreparationTimeUseCase(mealsRepositoryImpl)
    val guessGameUIController = GuessGameUIController(getGuessPreparationTimeUseCase)

    val fastHealthyMealsUseCase = GetFastHealthyMealsUseCase(mealsRepositoryImpl)
    val fastHealthyMealsUiController = FastHealthyMealsUIController(fastHealthyMealsUseCase)

    val getSeafoodMealsByProteinUseCase = GetSeafoodMealsByProteinUseCase(mealsRepositoryImpl)
    val seafoodMealsSuccessUIController = SeafoodMealsSuccessUIController(getSeafoodMealsByProteinUseCase)

    val getKetoMealsUseCase = GetKetoMealsUseCase(mealsRepositoryImpl)
    val suggestTop10EasyMealsUIController = SuggestTop10EasyMealsUIController(SuggestTop10EasyMealsUseCase(mealsRepositoryImpl))


    val handlers = mapOf(
        1 to fastHealthyMealsUiController,
        4 to suggestTop10EasyMealsUIController,
        5 to guessGameUIController,
        7 to KetoMealHelperUIController(getKetoMealsUseCase),
        14 to seafoodMealsSuccessUIController
    )

    MainMenuHandler(handlers).start()

}