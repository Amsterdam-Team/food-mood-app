package org.example.data

import org.example.logic.MealsRepository
import org.example.models.ResultStatus
import java.io.File

class CSVMealsRepository(
    private val csvFoodParser: CSVFoodParser
): MealsRepository {
    override fun getAllMeals(): ResultStatus{
        return try {
            val meals = csvFoodParser.parseCsvFile2()
            ResultStatus.Success(meals)
        }catch (e:Exception){
            ResultStatus.Error(e)
        }

    }
    private fun parseOneLine(line: String){

    }
}