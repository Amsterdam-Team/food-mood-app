package presentation

import data.CSVMealsRepository
import org.example.data.CSVFoodFileReader
import org.example.data.CSVFoodParser
import java.io.File

fun main() {

    val file = File("food.csv")



    val csvFoodFileReader = CSVFoodFileReader(file)
    val csvParser = CSVFoodParser(csvFoodFileReader)

    val csvMealRepo = CSVMealsRepository(csvParser)
    val meals = csvMealRepo.getAllMeals()
    println(meals[0])
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


}
