package presentation.models

sealed class IngredientGameResult {
    data class GameInProgress(
        val mealName: String,
        val ingredientOptions: List<String>,
        val correctIngredient: String,
        val score: Int,
        val correctAnswers: Int
    ) : IngredientGameResult()

    data class GameFinished(
        val score: Int,
        val correctAnswers: Int
    ) : IngredientGameResult()
}
