package org.example.logic.search

import logic.MealsRepository
import logic.exception.FoodMoodException
import logic.models.Meal

class GetMealByNameUseCase (
    private val searchUsingKMP: SearchUsingKMP,
    private val mealsRepository: MealsRepository
){

    fun getMealDetails(mealName:String): List<Meal> {

        val allMealsNames :List<String>? = mealsRepository.getAllMeals().map { it.name.toString() }

        val correctMealName = searchUsingKMP.validateTheInputInExistData(mealName,allMealsNames)
        if(correctMealName == "Not Found")
            throw FoodMoodException.Validation.InvalidMealName

         val meal:List<Meal> = mealsRepository.getAllMeals().filter { it.name == correctMealName }

        return meal
    }
}