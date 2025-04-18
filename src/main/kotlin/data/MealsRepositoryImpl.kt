package data
import CSVFoodParser
import logic.MealsRepository
import logic.models.Meal


class MealsRepositoryImpl(private val csvFoodParser: CSVFoodParser) : MealsRepository {

    private var allMeals: List<Meal> = listOf()

    init {
        val meals = csvFoodParser.parseCsvFile()
        allMeals = meals
    }
    
    override fun getAllMeals(): List<Meal> {
        return allMeals
    }
}