package org.example.data

import org.example.logic.MealsRepository
import org.example.models.Meal
import java.io.File

class CSVMealsRepository(private val csvFile: File): MealsRepository {
    override fun getAllMeals(): List<Meal>{
        return emptyList()
    }
    private fun parseOneLine(line: String){

    }
}