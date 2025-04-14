package org.example.logic

import org.example.models.ResultStatus

interface MealsRepository {
    fun getAllMeals(): ResultStatus
}