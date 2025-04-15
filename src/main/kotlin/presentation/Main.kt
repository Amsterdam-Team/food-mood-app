package org.example.presentation

import org.example.data.CSVMealsRepository
import presentation.utils.getErrorMessageByException
import presentation.utils.loadingMessage
import java.io.File

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
fun main() {
    val meals = CSVMealsRepository(File("food.csv")).getAllMeals()

}

fun <T> tryToExecute(action: () -> T, onSuccess: (result: T) -> Unit) {
    println(loadingMessage())

    try {
        action().also { onSuccess(it) }
    } catch (exception: Exception) {
        println(getErrorMessageByException(exception))
    }
}


