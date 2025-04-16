package presentation
import logic.exception.FoodMoodException
import logic.usecase.SearchByIngredientsUseCase
import org.example.logic.EmptyDataException

class ILovePotatoUI(private val searchByIngredientsUseCase: SearchByIngredientsUseCase) {
    fun start() {
        println("Hi, potato lover!")

        val potatoMeals = searchByIngredientsUseCase.getMealByIngredient("potatoes")

        if (potatoMeals.isEmpty()) {
            throw EmptyDataException()
            return
        }

        println("Here are some meals that contain potatoes:")

        val displayedMeals = potatoMeals.shuffled().take(10)

        displayedMeals.forEachIndexed { index, meal ->
            println("${index + 1}. Meal: ${meal.name}")
            println("   Ingredients: ${meal.ingredients}")
        }
    }
}