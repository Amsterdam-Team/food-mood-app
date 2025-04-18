package presentation

import CSVFoodParser
import data.MealsRepositoryImpl
import logic.GetFastHealthyMealsUseCase
import logic.usecase.*
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

    val getIraqiMealsUseCase = GetIraqiMealsUseCase(mealsRepositoryImpl)
    val iraqiMealUIController = IraqiMealUIController(getIraqiMealsUseCase)

    val getItalianMealsForLargeGroupsUseCase = GetItalianMealsForLargeGroupsUseCase(mealsRepositoryImpl)
    val italianMealUIController = ItalianMealUIController(getItalianMealsForLargeGroupsUseCase)

    val handlers = mapOf(
        1 to fastHealthyMealsUiController,
        3 to iraqiMealUIController,
        5 to guessGameUIController,
        7 to KetoMealHelperUIController(getKetoMealsUseCase),
        14 to seafoodMealsSuccessUIController,
        15 to italianMealUIController
    )

    MainMenuHandler(handlers).start()

}