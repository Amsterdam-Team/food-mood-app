package org.example

import org.example.data.CSVMealsRepository
import java.io.File

fun main() {
    val meals  = CSVMealsRepository(File("food.csv")).getAllMeals()

}