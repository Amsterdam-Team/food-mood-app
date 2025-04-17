package presentation.uiController

import logic.models.Meal
import logic.usecase.GetSeafoodMealsByProteinUseCase
import presentation.utils.tryToExecute
import presentation.utils.withGreenColor

class SeafoodMealsSuccessUIController(private val getSeafoodMealsByProteinUseCase: GetSeafoodMealsByProteinUseCase) :
    BaseUIController {
    override fun execute() {
        tryToExecute(
            action = { getSeafoodMealsByProteinUseCase.getSeafoodMealsByProteinUseCase() },
            onSuccess = ::onGetSeafoodMealsSuccess
        )
    }

    private fun onGetSeafoodMealsSuccess(meals: List<Meal>) {
        val rankedMealSummaries = meals.mapIndexed { index, meal ->
            "Rank: ${index + 1} Meal Name: ${meal.name} Protein Amount: ${meal.nutrition?.protein}"
        }

        rankedMealSummaries.forEach {
            println(it.withGreenColor())
        }
    }
}


