package presentation

import logic.usecase.GetSeafoodMealsByProteinUseCase
import presentation.uiController.MainMenuHandler
import presentation.uiController.SeafoodMealsSuccessUIController
import data.MealsRepositoryImpl
import java.io.File

fun main() {
    val meals = CSVMealsRepository(File("food.csv")).getAllMeals()
    val csvFile = File("food.csv")
    val csvMealsRepository = CSVMealsRepository(csvFile)

    val getSeafoodMealsByProteinUseCase = GetSeafoodMealsByProteinUseCase(csvMealsRepository)
    val seafoodMealsSuccessUIController = SeafoodMealsSuccessUIController(getSeafoodMealsByProteinUseCase)

    val handlers = mapOf(
        14 to seafoodMealsSuccessUIController
    )

    MainMenuHandler(handlers).start()

}
