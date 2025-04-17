package org.example.data

import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import logic.exception.FoodMoodException
import logic.models.Meal
import logic.models.Nutrition
import java.time.DateTimeException

import java.time.LocalDate
import java.time.format.DateTimeParseException

class CSVFoodParser(private val csvFoodFileReader: CSVFoodFileReader) {
//
    fun parseCsvFile(): List<Meal> {
        val rows = csvReader().readAllWithHeader(csvFoodFileReader.csvFile)
        return rows
            .map { row ->

                val submittedDate = row["submitted"]?.takeIf { it.isNotBlank() }

                val submittedDateModel: LocalDate? = if(submittedDate != null) try {
                    LocalDate.parse(submittedDate)
                }catch (e:DateTimeParseException ){
                    null
                } else null

            Meal(
                name = row["name"]?.takeIf { it.isNotBlank() },
                description = row["description"]?.takeIf { it.isNotBlank() },
                id = row["id"]?.takeIf { it.isNotBlank() },
                contributorId = row["contributor_id"]?.takeIf { it.isNotBlank() },
                submittedDate =submittedDateModel,
                preparationTime = row["minutes"]?.takeIf { it.isNotBlank() }.toString().toIntOrNull(),
                tags = if(row["tags"] == null ) null else parseQuotedStringIntoList(row["tags"]?.takeIf { it.isNotBlank() }) ,
                nutrition = parseNutrition(row["nutrition"]?.takeIf { it.isNotBlank() }),
                steps = if(row["steps"] == null ) null else parseQuotedStringIntoList(row["steps"]?.takeIf { it.isNotBlank() }),
                numberOfSteps = row["n_steps"].toString().toIntOrNull(),
                ingredients = if(row["ingredients"] == null ) null else parseQuotedStringIntoList(row["ingredients"]?.takeIf { it.isNotBlank() }),
                numberOfIngredients = row["n_ingredients"].toString().toIntOrNull(),
            )
        }
    }

    private fun parseQuotedStringIntoList(text: String?):List<String>?{
        if(text.isNullOrBlank()) return null
        val regex = Regex("'(.*?)'")
        return regex.findAll(text).map {
            it.groupValues[1].trim()
        }.toList()
    }

    private fun parseNutrition(text: String?): Nutrition?{
        val cleanedText = text?.trim()?.trim('[',']','"') ?: return null
        val values:List<Double?> = cleanedText.split(",").map { it.trim().toDoubleOrNull() }
        return Nutrition(
                values.getOrNull(ColumnsIndexes.CALORIES),
                values.getOrNull(ColumnsIndexes.TOTAL_FAT),
                values.getOrNull(ColumnsIndexes.SUGAR),
                values.getOrNull(ColumnsIndexes.SODIUM),
                values.getOrNull(ColumnsIndexes.PROTEIN),
                values.getOrNull(ColumnsIndexes.SATURATED_FAT),
                values.getOrNull(ColumnsIndexes.CARBOHYDRATE)
        )
    }

    companion object {

        object ColumnsIndexes {

            // NUTRITION ARRAY
            final val CALORIES = 0
            final val TOTAL_FAT = 1
            final val SUGAR = 2
            final val SODIUM = 3
            final val PROTEIN = 4
            final val SATURATED_FAT = 5
            final val CARBOHYDRATE = 6
        }
    }
}