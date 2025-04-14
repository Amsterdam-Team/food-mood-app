package org.example.models

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