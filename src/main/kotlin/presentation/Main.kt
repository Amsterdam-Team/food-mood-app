package presentation

import data.CSVMealsRepository
import logic.GetFastHealthyMealsUseCase
import logic.usecase.GetSeafoodMealsByProteinUseCase
import presentation.uiController.FastHealthyMealsUIController
import presentation.uiController.MainMenuHandler
import presentation.uiController.SeafoodMealsSuccessUIController
import java.io.File

fun main() {
    val meals = CSVMealsRepository(File("food.csv")).getAllMeals()
    val csvMealsRepository = CSVMealsRepository(File("food.csv"))

    val getFastHealthyMealsUseCase = GetFastHealthyMealsUseCase(csvMealsRepository)
    val fastHealthyMealsUiController = FastHealthyMealsUIController(getFastHealthyMealsUseCase)

    val getSeafoodMealsByProteinUseCase = GetSeafoodMealsByProteinUseCase(csvMealsRepository)
    val seafoodMealsSuccessUIController = SeafoodMealsSuccessUIController(getSeafoodMealsByProteinUseCase)

    val handlers = mapOf(
        1 to fastHealthyMealsUiController,
        14 to seafoodMealsSuccessUIController
    )

    MainMenuHandler(handlers).start()

}
