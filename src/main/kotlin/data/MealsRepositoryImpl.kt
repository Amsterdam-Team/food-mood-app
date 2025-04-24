package data
import CSVFoodParser
import logic.MealsRepository
import logic.models.Meal


class MealsRepositoryImpl(private val csvFoodParser: CSVFoodParser, private val fileReader: CSVFoodFileReader) : MealsRepository {

    private var allMeals: List<Meal> = listOf()

    init {
        val rows:List<Map<String, String>> = fileReader.readFile()
         allMeals = rows.mapNotNull { row ->
             csvFoodParser.parseRow(row)
         }

    }
    
    override fun getAllMeals(): List<Meal> {
        return allMeals
    }
}