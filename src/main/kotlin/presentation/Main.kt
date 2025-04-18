package presentation

import CSVFoodParser
import data.MealsRepositoryImpl
import logic.GetFastHealthyMealsUseCase
import logic.usecase.GetKetoMealsUseCase
import logic.usecase.GetRandomOneSweetMealWithoutEggsUseCase
import logic.usecase.GetSeafoodMealsByProteinUseCase
import logic.usecase.GuessPreparationTimeUseCase
import org.example.data.CSVFoodFileReader
import presentation.uiController.*
import java.io.File

fun main() {
    val csvFile = File("food.csv")
    val mealsRepositoryImpl = MealsRepositoryImpl(CSVFoodParser(CSVFoodFileReader(csvFile)))




    val getGuessPreparationTimeUseCase = GuessPreparationTimeUseCase(mealsRepositoryImpl)
    val guessGameUIController = GuessGameUIController(getGuessPreparationTimeUseCase)

    val fastHealthyMealsUseCase = GetFastHealthyMealsUseCase(mealsRepositoryImpl)
    val fastHealthyMealsUiController = FastHealthyMealsUIController(fastHealthyMealsUseCase)

    val getSeafoodMealsByProteinUseCase = GetSeafoodMealsByProteinUseCase(mealsRepositoryImpl)
    val seafoodMealsSuccessUIController = SeafoodMealsSuccessUIController(getSeafoodMealsByProteinUseCase)

    val getKetoMealsUseCase = GetKetoMealsUseCase(mealsRepositoryImpl)
    val getRandomOneSweetMealWithoutEggsUseCase = GetRandomOneSweetMealWithoutEggsUseCase(mealsRepositoryImpl)


    val handlers = mapOf(
        1 to fastHealthyMealsUiController,
        5 to guessGameUIController,
        7 to KetoMealHelperUIController(getKetoMealsUseCase),
        6 to SweetMealsUIController(getRandomOneSweetMealWithoutEggsUseCase),
        14 to seafoodMealsSuccessUIController
    )

    MainMenuHandler(handlers).start()

}