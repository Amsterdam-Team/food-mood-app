package logic

import kotlinx.datetime.LocalDate
import logic.exception.FoodMoodException

object Utils {
    fun parseDate(inputDate: String): LocalDate? {
        return try {
            val date = LocalDate.parse(inputDate)
            date
        } catch (exception: Exception) {
            throw FoodMoodException.ParsingException.InvalidDateFormatException
        }
    }
}

