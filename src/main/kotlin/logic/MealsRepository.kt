package org.example.logic

import org.example.models.Meal

interface MealsRepository {
    fun getAllMeals(): List<Meal>
}