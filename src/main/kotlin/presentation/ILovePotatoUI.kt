package presentation
import logic.usecase.SearchByIngredientsUseCase
import presentation.uiController.BaseUIController
import presentation.utils.tryToExecute

class ILovePotatoUI(private val searchByIngredientsUseCase: SearchByIngredientsUseCase): BaseUIController {

    override fun execute() {
        tryToExecute (
            action = {startILovePotatoGame()},
            onSuccess = {
                println("enjoy your meal!")
            }
        )
    }
    fun startILovePotatoGame() {
        println("Hi, potato lover!")
        val potatoMeals = searchByIngredientsUseCase.getMealByIngredient("potatoes")
        println("Here are some meals that contain potatoes:")
        val displayedMeals = potatoMeals.shuffled().take(10)
        displayedMeals.forEachIndexed { index, meal ->
            println("${index + 1}. Meal: ${meal.name}")
            println("   Ingredients: ${meal.ingredients}")
        }
    }
}