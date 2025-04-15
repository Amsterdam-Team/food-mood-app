package org.example

sealed class FoodMoodException(message:String): Exception(){
    class CountryName (message:String): FoodMoodException(message)
}
