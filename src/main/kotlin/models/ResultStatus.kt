package org.example.models
import kotlin.collections.List

sealed class ResultStatus {
    class Success(val meals: List<Meal>) : ResultStatus()
    class Error(val exception: Exception) : ResultStatus()
    class Loading : ResultStatus()
}