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

    /**
     ********* Executes a given use case safely and handles success and failure cases. *********
     * @sample
     *  tryToExecute(
     *      action = { GetFastHealthyMealsUseCase(CSVMealsRepository(File("food.csv"))).getFastHealthMeals() },
     *      onSuccess = ::onGetFastHealthyMealsSuccess
     *  )
     *
     * fun onGetFastHealthyMealsSuccess(meals: List<Meal>) {
     *     meals.forEach { println(it.toString().withGreenColor()) }
     * }
     *
     * @param action The block of use case function to execute.
     *               This should be where your core logic goes, like fetching or processing data.
     * @param onSuccess A callback invoked if the action completes successfully.
     *                  Use this to handle the result (e.g., print output).
     *
     * @see getErrorMessageByException Used internally to convert known exceptions into readable error messages.
     *      Make sure to add your specific custom exceptions inside `getErrorMessageByException` to handle them properly.
     */

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
