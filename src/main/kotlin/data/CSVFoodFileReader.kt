package data

import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import com.github.doyaaaaaken.kotlincsv.util.MalformedCSVException
import java.io.File

class CSVFoodFileReader(val csvFile: File) {

    fun readFile(): List<Map<String,String>>{
        return try{
            csvReader().readAllWithHeader(csvFile)
        } catch(e: MalformedCSVException){
            println("malformed CSV file")
            emptyList<Map<String,String>>()
        } catch (e:Exception){
            emptyList<Map<String,String>>()
        }
    }

}