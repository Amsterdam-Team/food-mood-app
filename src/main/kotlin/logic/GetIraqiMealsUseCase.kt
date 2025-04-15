package org.example.logic

import org.example.models.Meal

class GetIraqiMealsUseCase(
    private val mealsRepository: MealsRepository
) {
    fun getOnlyIraqiMeals(): List<Meal>{
        return mealsRepository.getAllMeals()
            .filter(::isHighQualityData)
            .filter { it.description!!.contains("Iraq", ignoreCase = true) ||
                it.tags!!.contains("iraqi")
            }
    }
    private fun isHighQualityData(meal: Meal): Boolean{
        return meal.description != null && meal.tags != null
    }
}