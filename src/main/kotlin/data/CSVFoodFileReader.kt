package org.example.data

import java.io.File

class CSVFoodFileReader(val csvFile: File) {

    fun readFile(): List<String>{
        return csvFile.readLines()
    }

}