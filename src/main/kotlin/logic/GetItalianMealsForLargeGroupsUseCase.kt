package logic

import logic.models.Meal

class GetItalianMealsForLargeGroupsUseCase(
    private val mealsRepository: MealsRepository
) {

    fun getItalianMealsForLargeGroups(): List<Meal>{
        return mealsRepository.getAllMeals()
            .filter(::isHighQualityData)
            .filter(::isItalianMealForLargeGroups)
    }

    private fun isItalianMealForLargeGroups(meal: Meal): Boolean{
        return (meal.tags?.contains("italian")?:false || meal.name?.contains("italian", ignoreCase = true)?:false) &&  meal.tags?.contains("for-large-groups")?: false
    }

    private fun isHighQualityData(meal: Meal): Boolean{
        return meal.tags != null && meal.description != null && meal.name != null
    }
}