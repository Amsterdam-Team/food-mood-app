package logic.models

import java.time.LocalDate

data class Meal(
    val id: String?,
    val name: String?,
    val description: String?,
    val preparationTime: Int?,
    val contributorId: String?,
    val submittedDate: LocalDate?,
    val tags: List<String>?,
    val nutrition: Nutrition?,
    val steps: List<String>?,
    val numberOfSteps: Int?,
    val ingredients: List<String>?,
    val numberOfIngredients: Int?
)

fun Meal.isSeafood(): Boolean {
    val seafoodKeywords = listOf("fish", "shrimp", "salmon", "tuna", "crab", "lobster", "seafood", "anchovy", "sardine")

    val lowerName = name?.lowercase().orEmpty()
    val lowerIngredients = ingredients?.joinToString(" ")?.lowercase().orEmpty()
    val lowerTags = tags?.joinToString(" ")?.lowercase().orEmpty()

    return seafoodKeywords.any { keyword ->
        keyword in lowerName || keyword in lowerIngredients || keyword in lowerTags
    }
}