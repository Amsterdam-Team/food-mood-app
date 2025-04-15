package org.example.logic

import org.example.models.Meal

class GetItalianMealsForLargeGroupsUseCase(
    private val mealsRepository: MealsRepository
) {

    fun getItalianMealsForLargeGroups(): List<Meal>{
        return mealsRepository.getAllMeals()
            .filter(::getOnlyHighQualityMeals)
            .filter { (it.tags!!.contains("italian") || it.name!!.contains("italian", ignoreCase = true) || it.description!!.contains("italy")) && it.tags!!.contains("for-large-groups") }
    }

    private fun getOnlyHighQualityMeals(meal: Meal): Boolean{
        return meal.tags != null && meal.description != null && meal.name != null
    }
}