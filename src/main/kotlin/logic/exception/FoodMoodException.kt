package logic.exception

sealed class FoodMoodException() : Exception() {
    sealed class Validation() : FoodMoodException() {
        data object InvalidCalories : Validation()
        data object InvalidProtein : Validation()
        data object EmptyMealName : Validation()
        data object NotFoundMealName: Validation()
        data object NotFoundCountryName: Validation()



    }

    sealed class ParsingException() : FoodMoodException() {
        data object MissingNutritionField : ParsingException()
    }

    sealed class GameException() : FoodMoodException() {
        data object AttemptsExceeded : GameException()
        data object WrongGuessFormat : GameException()

        // // Ingredient Game Exceptions
        data object InvalidUserInput : GameException()
        data object MealDataCorrupted : GameException()
        data object IngredientOptionsNotEnough : GameException()
        data object NoMealsAvailable : GameException()
        data object GameLoad : GameException()

    }

    sealed class EmptyDataException : FoodMoodException()
}