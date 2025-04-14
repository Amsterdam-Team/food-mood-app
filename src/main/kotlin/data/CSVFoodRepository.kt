package org.example.data

import org.example.logic.MealsRepository
import org.example.models.ResultStatus
import java.io.File

class CSVMealsRepository(
    private val csvFile: File
): MealsRepository {
    override fun getAllMeals(): ResultStatus{

        return ResultStatus.Success(emptyList())
    }
    private fun parseOneLine(line: String){

    }
}