package logic.usecase

import logic.MealsRepository
import logic.exception.FoodMoodException
import logic.models.Meal

class GetItalianMealsForLargeGroupsUseCase(
    private val mealsRepository: MealsRepository
) {

    fun getItalianMealsForLargeGroups(): List<Meal>{
        val italianMeals = mealsRepository.getAllMeals()
            .filter(::isHighQualityData)
            .filter(::isItalianMealForLargeGroups)
        return if (italianMeals.isNotEmpty()){
            italianMeals
        } else{
            throw FoodMoodException.Validation.EmptyDataException
        }
    }

    private fun isItalianMealForLargeGroups(meal: Meal): Boolean{
        return (meal.tags?.contains("italian")?:false || meal.name?.contains("italian", ignoreCase = true)?:false) &&  meal.tags?.contains("for-large-groups")?: false
    }

    private fun isHighQualityData(meal: Meal): Boolean{
        return meal.tags != null && meal.description != null && meal.name != null
    }
}