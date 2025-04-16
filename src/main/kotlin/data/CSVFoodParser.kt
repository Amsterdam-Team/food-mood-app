package org.example.data

import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import logic.models.Meal
import logic.models.Nutrition

import java.time.LocalDate

class CSVFoodParser(private val csvFoodFileReader: CSVFoodFileReader) {
//
    fun parseCsvFile(): List<Meal> {
        val rows = csvReader().readAllWithHeader(csvFoodFileReader.csvFile)
        return rows
            .map { row ->

                val nutritionList = if(row["nutrition"] == null) null  else(row["nutrition"] as String ).trim().trim(){
                    it == ',' || it == '"' || it == ']' || it == '['
                }.split(",").map { nutritionItem ->
                    nutritionItem.trim().trim { it == '\''}
                    nutritionItem.toDoubleOrNull()
                }

            Meal(
                name = row["name"] as String,
                description = row["description"] as String,
                id = row["id"] as String,
                contributorId = row["contributor_id"] as String,
                submittedDate = LocalDate.parse(row["submitted"] as String),
                preparationTime = row["minutes"].toString().toIntOrNull(),
                tags = if(row["tags"] == null ) null else parseQuotedStringIntoList(row["tags"] as String) ,
                nutrition = if(nutritionList == null) null else Nutrition(
                    nutritionList[ColumnsIndexes.CALORIES],
                    nutritionList[ColumnsIndexes.TOTAL_FAT],
                    nutritionList[ColumnsIndexes.SUGAR],
                    nutritionList[ColumnsIndexes.SODIUM],
                    nutritionList[ColumnsIndexes.PROTEIN],
                    nutritionList[ColumnsIndexes.SATURATED_FAT],
                    nutritionList[ColumnsIndexes.CARBOHYDRATE]
                ),
                steps = if(row["steps"] == null ) null else parseQuotedStringIntoList(row["steps"] as String),
                numberOfSteps = row["n_steps"].toString().toIntOrNull(),
                ingredients = if(row["ingredients"] == null ) null else parseQuotedStringIntoList(row["ingredients"] as String),
                numberOfIngredients = row["n_ingredients"].toString().toIntOrNull(),
            )
        }
    }

    private fun parseQuotedStringIntoList(text: String):List<String>{
        val regex = Regex("'(.*?)'")
        return regex.findAll(text).map {
            it.groupValues[1].trim()
        }.toList()
    }
}