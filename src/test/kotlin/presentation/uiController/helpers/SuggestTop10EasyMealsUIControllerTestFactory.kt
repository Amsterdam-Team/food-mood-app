package presentation.uiController.helpers

import logic.helpers.createMeal
import logic.models.Meal


object SuggestTop10EasyMealsUIControllerTestFactory {
    const val EMPTY_DATA_EXCEPTION_ERROR_MESSAGE = "No meals found"

    val formattedSuggestTop10EasyMeals =   """
    Loading meals...
    Suggest Easy Meal 
     1 - Meal Name: simple berry smoothie,   
     2 - Meal Name: pineapple leap,   
     3 - Meal Name: world s richest and most delicious cheesecake,   
     4 - Meal Name: angel s kiss,   
     5 - Meal Name: easy mexican chicken,   
     6 - Meal Name: long lady tea,   
     7 - Meal Name: sweet mustard sauce,   
     8 - Meal Name: old fashioned red candied apples,   
     9 - Meal Name: homemade chocolate spread,    
     10 - Meal Name: cinnamon raisin banana cheese muffins,    
""".trimIndent()

    val noSpaces = formattedSuggestTop10EasyMeals.replace("\\s+".toRegex(), "")

    fun generateSampleSuggestEasyMeals(): List<Meal> = listOf(
        createMeal("simple berry smoothie"),
        createMeal("pineapple leap"),
        createMeal("world s richest and most delicious cheesecake"),
        createMeal("angel s kiss"),
        createMeal("easy mexican chicken"),
        createMeal("long lady tea"),
        createMeal("sweet mustard sauce"),
        createMeal("old fashioned red candied apples"),
        createMeal("homemade chocolate spread"),
        createMeal("cinnamon raisin banana cheese muffins")
    )
}
