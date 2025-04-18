package logic

import kotlinx.datetime.LocalDate
import logic.exception.FoodMoodException
import java.time.format.DateTimeParseException

object Utils {
    fun parseDate(inputDate: String): LocalDate? {
        return try {
            val date = LocalDate.parse(inputDate)
            date
        } catch (exception: DateTimeParseException) {
            throw FoodMoodException.ParsingException.InvalidDateFormatException
        }
    }
}

