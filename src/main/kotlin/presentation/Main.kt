package presentation

import CSVFoodParser
import data.MealsRepositoryImpl
import logic.usecase.GetSeafoodMealsByProteinUseCase
import logic.usecase.GuessPreparationTimeUseCase
import org.example.data.CSVFoodFileReader
import presentation.uiController.GuessGameUIController
import presentation.uiController.MainMenuHandler
import presentation.uiController.SeafoodMealsSuccessUIController
import java.io.File

fun main() {
    val csvFile = File("food.csv")
    val mealsRepositoryImpl = MealsRepositoryImpl(CSVFoodParser(CSVFoodFileReader(csvFile)))
    val meals = MealsRepositoryImpl(CSVFoodParser(CSVFoodFileReader(csvFile))).getAllMeals()


    val getGuessPreparationTimeUseCase = GuessPreparationTimeUseCase(mealsRepositoryImpl)
    val guessGameUIController = GuessGameUIController(getGuessPreparationTimeUseCase)
    val getSeafoodMealsByProteinUseCase = GetSeafoodMealsByProteinUseCase(mealsRepositoryImpl)
    val seafoodMealsSuccessUIController =
        SeafoodMealsSuccessUIController(getSeafoodMealsByProteinUseCase)

    val handlers = mapOf(
        5 to guessGameUIController,
        14 to seafoodMealsSuccessUIController
    )

    MainMenuHandler(handlers).start()

}