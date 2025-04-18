package logic.models

data class IngredientGameState(
    val usedMealIds: MutableSet<String> = mutableSetOf(),
    var score: Int = 0,
    var correctAnswers: Int = 0
) {
    fun isFinished(): Boolean = correctAnswers >= 15
    fun increaseScore() {
        score += 1000
        correctAnswers++
    }
}
