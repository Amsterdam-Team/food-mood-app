package data

import logic.MealsRepository
import logic.models.Meal
import java.io.File

class MealsRepositoryImpl(private val csvFile: File) : MealsRepository {
    override fun getAllMeals(): List<Meal> {
        return emptyList()
    }

    private fun parseOneLine(line: String) {

    }
}