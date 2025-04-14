package org.example.models
import kotlin.collections.List

sealed class ResultStatus {
    class Success(meals : List<Meal>): ResultStatus()
    class Error(exception: Exception): ResultStatus()
    class Loading(): ResultStatus()
}