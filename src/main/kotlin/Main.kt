package org.example

import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import data.CSVMealsRepository
import org.example.data.CSVFoodFileReader
import org.example.data.CSVFoodParser

import java.io.File
import kotlin.reflect.typeOf

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
fun main() {
//    val meals  = CSVMealsRepository(File("food.csv")).getAllMeals()
    val file = File("food.csv")



    val csvFoodFileReader = CSVFoodFileReader(file)
    val csvParser = CSVFoodParser(csvFoodFileReader)

    val csvMealRepo = CSVMealsRepository(csvParser)
    val result = csvMealRepo.getAllMeals()


    println( result.filter {
        it.ingredients != null && it.ingredients.contains("potatoes")
    }.count()
    )


    println("--> ${result[0].tags}")

    }



