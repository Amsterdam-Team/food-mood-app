package logic.exception

sealed class FoodMoodException() : Exception() {
    sealed class Validation() : FoodMoodException() {
        data object InvalidCalories : Validation()
        data object InvalidProtein : Validation()
        data object EmptyMealName : Validation()
    }

    sealed class ParsingException() : FoodMoodException() {
        data object MissingNutritionField : ParsingException()
    }

    sealed class GameException() : FoodMoodException() {
        data object AttemptsExceeded : GameException()
        data object WrongGuessFormat : GameException()
    }

    sealed class EmptyDataException : FoodMoodException()
}