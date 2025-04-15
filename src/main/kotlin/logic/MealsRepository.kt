package org.example.logic

import logic.models.Meal

interface MealsRepository {
    fun getAllMeals(): List<Meal>
}