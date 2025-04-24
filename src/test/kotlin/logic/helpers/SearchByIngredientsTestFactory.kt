package logic.helpers


import logic.models.Meal


object SearchByIngredientsTestFactory {

    const val POTATOES = "potatoes"
    const val LEMON = "lemon"
    const val GARLIC = "garlic"

    fun makePotatoMeals(count: Int): List<Meal> =
        List(count) { index ->
            createMeal(
                name = "Potato Meal ${index + 1}",
                ingredients = listOf(POTATOES, "ingredient ${index + 1}")
            )
        }

    fun makeSinglePotatoMeal(): Meal =
        createMeal(
            name = "Mashed Potatoes",
            ingredients = listOf(POTATOES, "butter", "milk")
        )

    fun makeMealsWithAndWithout(targetIngredient: String): Pair<Meal, Meal> {
        val with = createMeal(
            name = "Dish with $targetIngredient",
            ingredients = listOf("salt", targetIngredient, "oil")
        )
        val without = createMeal(
            name = "Dish without $targetIngredient",
            ingredients = listOf("chicken", "pepper")
        )
        return Pair(with, without)
    }

    fun makeSomeMeals(): List<Meal> = listOf(
        createMeal(name = "Shrimp", ingredients = listOf("shrimp", "salt")),
        createMeal(name = "Tuna", ingredients = listOf("tuna", "pepper"))
    )

    fun makeMealsWithNullAndValidIngredient(targetIngredient: String): List<Meal> = listOf(
        createMeal(name = "Null Ingredients Meal"),
        createMeal(name = "Valid Meal", ingredients = listOf(targetIngredient))
    )
}
