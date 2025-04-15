package org.example.data

import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import logic.models.Meal
import logic.models.Nutrition

import java.time.LocalDate

class CSVFoodParser(val csvFoodFileReader: CSVFoodFileReader) {
//
    fun parseCsvFile2(): List<Meal> {
        val rows = csvReader().readAllWithHeader(csvFoodFileReader.csvFile)
        return rows
            .map {
            val nutritionList = (it["nutrition"] as String ).trim().trim(){
                it == ',' || it == '"' || it == ']' || it == '['
            }.split(",")

            val stepsList = (it["steps"] as String).trim().trim(){
                it == ',' || it == '"' || it == ']' || it == '['
            }.split(",")
            val ingredientList = (it["ingredients"] as String).trim().trim(){
                it == ',' || it == '"' || it == ']' || it == '['
            }.split(",")
            Meal(
                name = it["name"] as String,
                description = it["description"] as String,
                id = it["id"] as String,
                contributorId = it["contributor_id"] as String,
                submittedDate = LocalDate.parse(it["submitted"] as String),
                preparationTime = it["minutes"].toString().toIntOrNull(),
                tags = it["tags"]?.trim()?.trim(){
                    it == ',' || it == '"' || it == ']' || it == '['
                }?.split(","),
                nutrition = Nutrition(
                    nutritionList[0].toString().toDoubleOrNull(),
                    nutritionList[1].toString().toDoubleOrNull(),
                    nutritionList[2].toString().toDoubleOrNull(),
                    nutritionList[3].toString().toDoubleOrNull(),
                    nutritionList[4].toString().toDoubleOrNull(),
                    nutritionList[5].toString().toDoubleOrNull(),
                    nutritionList[6].toString().toDoubleOrNull()
                ),
                steps = stepsList,
                numberOfSteps = it["n_steps"].toString().toIntOrNull(),
                ingredients = ingredientList,
                numberOfIngredients = it["n_ingredients"].toString().toIntOrNull(),
            )
        }
    }
    fun parseCsvFile(): List<Meal>{
        val mealList:List<Meal> = csvFoodFileReader.readFile().run {
            slice(1..4)
        }.map { it->

            val infoList = extractInfo(it)
            println("info list length ::: ${(infoList).size}")
            val nutritionList = infoList[ColumnsIndexes.NUTRITION] as List<String>
            Meal(
                name = infoList[ColumnsIndexes.NAME] as String?,
                id = infoList[ColumnsIndexes.ID] as String?,
                description = infoList[ColumnsIndexes.DESCRIPTION] as String?,
                preparationTime = infoList[ColumnsIndexes.MINUTES] as Int?,
                contributorId = infoList[ColumnsIndexes.CONTRIBUTER_ID]as String?,
                submittedDate = LocalDate.parse( (infoList[ColumnsIndexes.SUBMITTED]as String )),
                tags = infoList[ColumnsIndexes.TAGS] as List<String>? ,
                nutrition =  Nutrition(
                      calories = nutritionList[0].toString().toDoubleOrNull(),
                        nutritionList[1].toString().toDoubleOrNull(),
             nutritionList[2].toString().toDoubleOrNull(),
              nutritionList[3].toString().toDoubleOrNull(),
             nutritionList[4].toString().toDoubleOrNull(),
            nutritionList[5].toString().toDoubleOrNull(),
                    nutritionList[6].toString().toDoubleOrNull()
                ),
                steps = infoList[ColumnsIndexes.STEPS] as List<String> ,
                numberOfSteps = infoList[ColumnsIndexes.N_STEPS] as Int?,
                ingredients = infoList[ColumnsIndexes.INGREDIENTS] as List<String>?,
                numberOfIngredients = infoList[ColumnsIndexes.N_INGREDIENTS] as Int?,
            )
        }
        return mealList
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


}