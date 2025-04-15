package org.example.presentation

import org.example.dummyMeals
import org.example.logic.GetFastHealthyMealsUseCase
import presentation.utils.getErrorMessageByException
import presentation.utils.loadingMessage
import presentation.utils.withGreenColor

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
fun main() {
    //val meals = CSVMealsRepository(File("food.csv")).getAllMeals()

    tryToExecute(
        action = { GetFastHealthyMealsUseCase().getFastHealthMeals(dummyMeals) },
        onSuccess = { println(it.toString().withGreenColor()) }
    )
}

fun <T> tryToExecute(action: () -> T, onSuccess: (result: T) -> Unit) {
    println(loadingMessage())

    try {
        action().also { onSuccess(it) }
    } catch (exception: Exception) {
        println(getErrorMessageByException(exception))
    }
}


