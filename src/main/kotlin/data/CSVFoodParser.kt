import kotlinx.datetime.LocalDate
import logic.models.Meal
import logic.models.Nutrition


class CSVFoodParser() {

    fun parseRow(row:Map<String,String>): Meal? {

        if(row.isEmpty()){
            return null
        }
                val submittedDate = row["submitted"]?.takeIf { it.isNotBlank() }

                val submittedDateModel: LocalDate? = if(submittedDate != null) try {
                    LocalDate.parse(submittedDate)
                }catch (e:Exception ){
                    null
                } else null

           return Meal(
                name = row["name"]?.takeIf { it.isNotBlank() },
                description = row["description"]?.takeIf { it.isNotBlank() },
                id = row["id"]?.takeIf { it.isNotBlank() },
                contributorId = row["contributor_id"]?.takeIf { it.isNotBlank() },
                submittedDate =submittedDateModel,
                preparationTime = row["minutes"]?.takeIf { it.isNotBlank() }.toString().toIntOrNull(),
                tags = parseQuotedStringIntoList(row["tags"]?.takeIf { it.isNotBlank() }) ,
                nutrition = parseNutrition(row["nutrition"]),
                steps = parseQuotedStringIntoList(row["steps"]?.takeIf { it.isNotBlank() }),
                numberOfSteps = row["n_steps"].toString().toIntOrNull(),
                ingredients = parseQuotedStringIntoList(row["ingredients"]?.takeIf { it.isNotBlank() }),
                numberOfIngredients = row["n_ingredients"].toString().toIntOrNull(),
            )
        }


    private fun parseQuotedStringIntoList(text: String?):List<String>?{
        if(text.isNullOrBlank()) return null
        val regex = Regex("'(.*?)'")
        return regex.findAll(text).map {
            it.groupValues[1].trim()
        }.toList()
    }

    private fun parseNutrition(text: String?): Nutrition?{
        if(text.isNullOrBlank()) return null
        val cleanedText = text.trim().trim('[',']','"')
        val values:List<Double?> = cleanedText.split(",").map { it.trim().toDoubleOrNull() }.takeIf { it.size ==7 } ?: return null

        return Nutrition(
                values.getOrNull(CALORIES),
                values.getOrNull(TOTAL_FAT),
                values.getOrNull(SUGAR),
                values.getOrNull(SODIUM),
                values.getOrNull(PROTEIN),
                values.getOrNull(SATURATED_FAT),
                values.getOrNull(CARBOHYDRATE)
        )
    }

    private companion object {

            // NUTRITION ARRAY
            const val CALORIES = 0
            const val TOTAL_FAT = 1
            const val SUGAR = 2
            const val SODIUM = 3
            const val PROTEIN = 4
            const val SATURATED_FAT = 5
            const val CARBOHYDRATE = 6


    }
}