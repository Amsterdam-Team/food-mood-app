package logic.exception

sealed class FoodMoodException() : Exception() {
    sealed class Validation() : FoodMoodException() {
        data object InvalidCalories : Validation()
        data object InvalidProtein : Validation()
        data object EmptyMealName : Validation()
        data object NoMealsWereFoundForTheGivenDate : Validation()
        data object NotFoundMealName: Validation()
        data object NotFoundCountryName: Validation()
        data object EmptyDataException : Validation()
        data object MissingPreparationTime : Validation()
        data object MealNotFounded : Validation()


    }

    sealed class ParsingException() : FoodMoodException() {
        data object MissingNutritionField : ParsingException()
        data object InvalidDateFormatException : ParsingException()

    }

    sealed class GameException() : FoodMoodException() {
        data object AttemptsExceeded : GameException()
        data object WrongGuessFormat : GameException()
    }

    sealed class EmptyDataException : FoodMoodException()
}