package presentation.utils

import logic.exception.FoodMoodException


fun String.withRedColor(): String = "\u001b[31m $this\u001B[0m"
fun String.withGreenColor(): String = "\u001B[32m $this\u001B[0m"
fun String.withYellowColor(): String = "\u001B[33m$this\u001B[0m"


fun getErrorMessageByException(exception: Exception): String {
    val message = when (exception) {
        is FoodMoodException.Validation.EmptyDataException -> "No meals found that match your criteria."
        is FoodMoodException.Validation.NoMoreSuggestion -> "No more meals contain over than 700 calorie"
        is FoodMoodException.Validation.NoMoreKetoMeals -> "No more keto meals"

        is FoodMoodException.Validation.InvalidCalories -> "Calories value is invalid. Please enter a number within the acceptable range."
        is FoodMoodException.Validation.InvalidProtein -> "Protein value is invalid. Please check the input."
        is FoodMoodException.Validation.EmptyMealName -> "Meal name cannot be empty. Please provide a valid name."
        is FoodMoodException.Validation.MissingPreparationTime -> "This meal has no preparation time. Unable to start the game."
        is FoodMoodException.Validation.MealNotFounded -> "No meals found that match your criteria."
        is FoodMoodException.Validation.NoMealsWereFoundForTheGivenDate -> "No Meals Were found for the given date"
        is FoodMoodException.ParsingException.InvalidDateFormatException -> "please enter a valid date format like yyy/mm/dd"
        is FoodMoodException.ParsingException.MissingNutritionField -> "Nutrition information is incomplete or missing. Please check the data source."

        is FoodMoodException.GameException.AttemptsExceeded -> "You’ve used all your attempts. Better luck next time!"
        is FoodMoodException.GameException.WrongGuessFormat -> "Invalid input format. Please enter a valid number for preparation time."

        is FoodMoodException.Validation.NotFoundMealName -> "This meal name not found , Please Try again and make sure of entering correct name "
        is FoodMoodException.Validation.NotFoundCountryName -> "This country name not found , Please Try again and make sure of entering correct name "

        is FoodMoodException.ParsingException.EmptyFileException -> "this csv file is empty"
        is FoodMoodException.ParsingException.MalFormedCsvFileException -> "this csv file is malformed."

        is FoodMoodException -> "Something went wrong with your request. Please try again."
        else -> "An unexpected error occurred. Please try again later."
    }

    return message.withRedColor()
}


fun loadingMessage(): String = "Loading meals...".withYellowColor()