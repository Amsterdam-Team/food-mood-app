package presentation.utils

import org.example.logic.EmptyDataException
import org.example.logic.FoodMoodException

fun String.withRedColor(): String = "\u001b[31m $this"
fun String.withGreenColor(): String = "\u001B[32m $this"
fun String.withYellowColor(): String = "\u001B[33m$this\u001B[0m"


fun getErrorMessageByException(exception: Exception): String {
    return when (exception) {
        is EmptyDataException -> "No meals found that match your criteria."
        is FoodMoodException -> "Something went wrong with your request. Please try again."
        else -> "An unexpected error occurred. Please try again later."
    }.withRedColor()
}

fun loadingMessage(): String = "Loading meals...".withYellowColor()
