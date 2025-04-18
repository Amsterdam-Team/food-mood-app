package presentation

import data.MealsRepositoryImpl
import logic.GetFastHealthyMealsUseCase
import logic.usecase.GetKetoMealsUseCase
import logic.usecase.GetSeafoodMealsByProteinUseCase
import logic.usecase.GuessPreparationTimeUseCase
import presentation.uiController.FastHealthyMealsUIController
import presentation.uiController.GuessGameUIController
import presentation.uiController.MainMenuHandler
import presentation.uiController.SeafoodMealsSuccessUIController
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


    val handlers = mapOf(
        1 to fastHealthyMealsUiController,
        5 to guessGameUIController,
        7 to KetoMealHelperUIController(getKetoMealsUseCase),
        14 to seafoodMealsSuccessUIController
    )

    MainMenuHandler(handlers).start()

}