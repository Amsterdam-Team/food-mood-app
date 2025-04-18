package presentation.uiController

import logic.usecase.SearchByIngredientsUseCase
import presentation.utils.getRandomElements
import presentation.utils.tryToExecute

class ILovePotatoUIController(private val searchByIngredientsUseCase: SearchByIngredientsUseCase): BaseUIController {

    override fun execute() {
        tryToExecute(
            action = { searchByIngredientsUseCase.getMealByIngredient("potatoes") },
            onSuccess = { potatoMeals ->
                println("Hi, potato lover!")
                println("Here are some meals that contain potatoes:")
                val displayedMeals = potatoMeals.getRandomElements(10)
                displayedMeals.forEachIndexed { index, meal ->
                    println("${index + 1}. Meal: ${meal.name}")
                    println("   Ingredients: ${meal.ingredients}")
                }
                println("enjoy your meal!")
            }
        )
    }

}