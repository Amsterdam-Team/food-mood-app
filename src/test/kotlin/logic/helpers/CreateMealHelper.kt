package logic.helpers

import logic.models.Meal

fun createMeal(
    name: String?,
    tags: List<String>?,
    description: String?
) = Meal(
    id = null,
    name = name,
    tags = tags,
    description = description,
    contributorId = null,
    nutrition = null,
    numberOfIngredients = null,
    numberOfSteps = null,
    submittedDate = null,
    steps = null,
    ingredients = null,
    preparationTime = null
)