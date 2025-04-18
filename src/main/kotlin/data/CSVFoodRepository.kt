package data

import logic.models.Meal
import logic.MealsRepository
import org.example.data.CSVFoodParser

class CSVMealsRepository(
    private val csvFoodParser: CSVFoodParser

): MealsRepository {
    private var allMeals: List<Meal> = listOf()

    init {
        val meals = csvFoodParser.parseCsvFile()
        allMeals = meals
    }
    override fun getAllMeals(): List<Meal> {
        return allMeals
    }
}