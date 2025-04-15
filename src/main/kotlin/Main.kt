package org.example

import org.example.data.CSVMealsRepository
import java.io.File

//TIP To <b>Run</b> cod+e, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
fun main() {
    val meals  = CSVMealsRepository(File("food.csv")).getAllMeals()

}