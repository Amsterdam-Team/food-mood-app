package presentation

import data.MealsRepositoryImpl
import java.io.File

fun main() {
    val meals = MealsRepositoryImpl(File("food.csv")).getAllMeals()
}
