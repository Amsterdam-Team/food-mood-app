package data

import logic.models.Meal
import org.example.logic.MealsRepository
import java.io.File

class CSVMealsRepository(private val csvFile: File) : MealsRepository {
    override fun getAllMeals(): List<Meal> {
        return emptyList()
    }

    private fun parseOneLine(line: String) {

    }
}