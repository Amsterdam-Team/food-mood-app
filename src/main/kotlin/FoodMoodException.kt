package org.example

sealed class FoodMoodException(message:String): Exception(){

    sealed class Validation(message :String): FoodMoodException(message){

        data object InvalidCountryName : Validation("This Country Not Found , Please Try again")

    }
}
