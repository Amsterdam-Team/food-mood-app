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
                val nutritionList: List<Double?>? = if(row["nutrition"] == null) null  else(row["nutrition"] as String ).trim().trim(){
                it == ',' || it == '"' || it == ']' || it == '['
            }.split(",").map { nutritionItem ->
                    nutritionItem.trim().trim { it == '\''}
                nutritionItem.toDoubleOrNull()
            }

            val stepsList = if(row["steps"] == null) null  else(row["steps"] as String).trim().trim(){
                it == ',' || it == '"' || it == ']' || it == '['
            }.split(",").map { step ->
                step.trim().trim { it == '\''}
            }
            val ingredientList = if(row["ingredients"] == null) null  else (row["ingredients"] as String).trim().trim(){
                it == ',' || it == '"' || it == ']' || it == '['
            }.split(",").map { ingredient ->
                ingredient.trim().trim { it == '\''}
            }
                val tagsList =  row["tags"]?.trim()?.trim(){
                    it == ',' || it == '"' || it == ']' || it == '['
                }?.split(",")?.map { tag ->
                    tag.trim().trim { it == '\''}
                }

            Meal(
                name = row["name"] as String,
                description = row["description"] as String,
                id = row["id"] as String,
                contributorId = row["contributor_id"] as String,
                submittedDate = LocalDate.parse(row["submitted"] as String),
                preparationTime = row["minutes"].toString().toIntOrNull(),
                tags = tagsList,
                nutrition = if(nutritionList == null) null else Nutrition(
                    nutritionList[ColumnsIndexes.CALORIES],
                    nutritionList[ColumnsIndexes.TOTAL_FAT],
                    nutritionList[ColumnsIndexes.SUGAR],
                    nutritionList[ColumnsIndexes.SODIUM],
                    nutritionList[ColumnsIndexes.PROTEIN],
                    nutritionList[ColumnsIndexes.SATURATED_FAT],
                    nutritionList[ColumnsIndexes.CARBOHYDRATE]
                ),
                steps = stepsList,
                numberOfSteps = row["n_steps"].toString().toIntOrNull(),
                ingredients = ingredientList,
                numberOfIngredients = row["n_ingredients"].toString().toIntOrNull(),
            )
        }
    }

}