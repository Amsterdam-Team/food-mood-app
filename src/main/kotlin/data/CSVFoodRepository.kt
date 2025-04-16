package data

import logic.models.Meal
import logic.MealsRepository
import org.example.data.CSVFoodParser

class CSVMealsRepository(
    private val csvFoodParser: CSVFoodParser

): MealsRepository {
    private var allMeals: List<Meal> = listOf()

    init {
        allMeals = try {
            val meals = csvFoodParser.parseCsvFile()
             meals
        } catch (e: Exception) {
            throw e
        }
    }
    override fun getAllMeals(): List<Meal> {
        return allMeals
    }
}