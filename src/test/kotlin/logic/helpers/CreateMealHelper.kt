package logic.helpers

import kotlinx.datetime.LocalDate
import logic.models.Meal
import logic.models.Nutrition

fun createMeal(
    name: String? = null,
    tags: List<String>? = null,
    description: String? = null,
    nutrition: Nutrition? = null,
    id: String? = null,
    contributorId: String? = null,
    numberOfIngredients: Int? = null,
    numberOfSteps: Int? = null,
    submittedDate: LocalDate? = null,
    steps: List<String>? = null,
    ingredients: List<String>? = null,
    preparationTime: Int? = null
) = Meal(
    id = id,
    name = name,
    tags = tags,
    description = description,
    contributorId = contributorId,
    nutrition = nutrition,
    numberOfIngredients = numberOfIngredients,
    numberOfSteps = numberOfSteps,
    submittedDate = submittedDate,
    steps = steps,
    ingredients = ingredients,
    preparationTime = preparationTime
)