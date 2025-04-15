package org.example

import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import data.CSVMealsRepository
import org.example.data.CSVFoodFileReader
import org.example.data.CSVFoodParser

import java.io.File

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
fun main() {
//    val meals  = CSVMealsRepository(File("food.csv")).getAllMeals()
    val file = File("food.csv")



    val csvFoodFileReader = CSVFoodFileReader(file)
    val csvParser = CSVFoodParser(csvFoodFileReader)

    val csvMealRepo = CSVMealsRepository(csvParser)
    val result = csvMealRepo.getAllMeals()
    println("--> ${result.size}")
    println("--> ${result.size}")
    println("--> ${result.size}")
//    println("--> count null steps: ${result.count {
//        it == null
//    }}")
    println(result.slice(0..2))
}

fun parseString(line: String){
    var index = 0
    var pointer = 0
    var mealList = mutableListOf<Any>()
    while (index < line.length){
        if(line[index] == ','){
            mealList.add(line.substring(pointer,index).trim())
            pointer = index +1
        }
        if (line[index] == '['){
            val closedPosition = line.indexOf(']', index+1)
            mealList.add(line.substring(index+1,closedPosition).split(","))
            index = closedPosition + 1
        }
        index++
    }
    println(mealList)
}


fun parseString2(line:String){

    var index = 0
    var pointer = 0
    val mealList = mutableListOf<Any>()

    while (index < line.length){
        if (line[index] == '['){
            mealList.add(line.substring(pointer,index).trim().split(','))
            pointer = index +1
        }
        if (line[index] == ']'){
            mealList.add(line.substring(pointer,index).split(","))
            pointer = index +1
        }
        index++
    }
    println(mealList)

}

fun extractInfo(line: String) :List<Any>{
    val tagStart = line.indexOf("[",0)
    val tagEnd = line.indexOf("]",tagStart)
    val tags = line.substring(tagStart, tagEnd+1).trim().trim(){
        it == ',' || it == '"' || it == ']' || it == '['
    }.split(",")

    val firstInfo = line.substring(0, tagStart).trim().trim(){
        it == ',' || it == '"' || it == ']' || it == '['
    }.split(",")



    val nutritionStart = line.indexOf("[",tagEnd)
    val nutritionEnd = line.indexOf("]", nutritionStart)
    val nutrition = line.substring(nutritionStart, nutritionEnd+1).trim().trim(){
        it == ',' || it == '"' || it == ']' || it == '['
    }.split(",")



    val stepsStart = line.indexOf("[",nutritionEnd)
    val stepsEnd = line.indexOf("]",stepsStart)
    val steps = line.substring(stepsStart, stepsEnd+1).trim().trim(){
        it == ',' || it == '"' || it == ']' || it == '['
    }.split(",")

    val stepsNum = line.substring(nutritionEnd, stepsStart).trim().trim(){
        it == ',' || it == '"' || it == ']' || it == '['
    }
    val endInfo = line.substring(stepsEnd+1, line.length).trim().trim(){
        it == ',' || it == '"' || it == ']' || it == '['
    }
    return firstInfo + listOf(tags) + listOf(nutrition) + listOf(stepsNum) + listOf(steps) + listOf(endInfo)

}

