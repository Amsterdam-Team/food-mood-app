package org.example.data

import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import logic.models.Meal
import logic.models.Nutrition

import java.time.LocalDate

class CSVFoodParser(val csvFoodFileReader: CSVFoodFileReader) {
//
    fun parseCsvFile(): List<Meal> {
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

}