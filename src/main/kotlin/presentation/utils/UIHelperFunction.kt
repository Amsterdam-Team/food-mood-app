package presentation.utils

import logic.exception.FoodMoodException


fun String.withRedColor(): String = "\u001b[31m $this\u001B[0m"
fun String.withGreenColor(): String = "\u001B[32m $this\u001B[0m"
fun String.withYellowColor(): String = "\u001B[33m$this\u001B[0m"


fun getErrorMessageByException(exception: Exception): String {
    val message = when (exception) {
        is FoodMoodException.Validation.EmptyDataException -> "No meals found that match your criteria."

        is FoodMoodException.Validation.InvalidCalories -> "Calories value is invalid. Please enter a number within the acceptable range."
        is FoodMoodException.Validation.InvalidProtein -> "Protein value is invalid. Please check the input."
        is FoodMoodException.Validation.EmptyMealName -> "Meal name cannot be empty. Please provide a valid name."

        is FoodMoodException.ParsingException.MissingNutritionField -> "Nutrition information is incomplete or missing. Please check the data source."

        is FoodMoodException.GameException.AttemptsExceeded -> "You’ve used all your attempts. Better luck next time!"
        is FoodMoodException.GameException.WrongGuessFormat -> "Invalid input format. Please enter a valid number for preparation time."

        is FoodMoodException -> "Something went wrong with your request. Please try again."

        is FoodMoodException.Validation.NotFoundMealName -> "This meal name not found , Please Try again and make sure of entering correct name "

        else -> "An unexpected error occurred. Please try again later."
    }

    return message.withRedColor()
}


fun loadingMessage(): String = "Loading meals...".withYellowColor()