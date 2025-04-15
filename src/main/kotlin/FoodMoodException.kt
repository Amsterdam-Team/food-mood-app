package org.example

sealed class FoodMoodException(message:String?): Exception(){

    sealed class Validation(message :String): FoodMoodException(message){

        data object InvalidCountryName : Validation("This Country Not Found , Please Try again")

        sealed class Parsing(message: String?) : FoodMoodException(message) {
            data class MissingNutritionField(val mealName: String?) :
                Parsing("Missing nutrition data for: $mealName")
        }

        sealed class Game(message: String?) : FoodMoodException(message) {
            data object AttemptsExceeded : Game("You exceeded the number of allowed guesses")
            data object WrongGuessFormat : Game("Invalid guess format. Please enter a valid number.")
        }

        data class Unknown(val reason: String? = null) :
            FoodMoodException(reason ?: "An unknown error occurred.")
    }

}
