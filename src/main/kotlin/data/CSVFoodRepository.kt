package data

import logic.models.Meal
import logic.MealsRepository
import org.example.data.CSVFoodParser

class CSVMealsRepository(
    private val csvFoodParser: CSVFoodParser
): MealsRepository {
    override fun getAllMeals(): List<Meal> {
        return try {
            val meals = csvFoodParser.parseCsvFile2()
            return meals
        } catch (e: Exception) {
            throw e
        }
    }
}