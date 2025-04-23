package data

import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import com.github.doyaaaaaken.kotlincsv.util.MalformedCSVException
import logic.exception.FoodMoodException.ParsingException.*
import java.io.File


class CSVFoodFileReader(val csvFile: File) {
    fun readFile(): List<Map<String,String>>{
        val rows = try{
            csvReader().readAllWithHeader(csvFile)
        }
        catch(e: MalformedCSVException){
            throw MalFormedCsvFileException
        }
        if (rows.isEmpty()) throw EmptyFileException
        return rows }
}