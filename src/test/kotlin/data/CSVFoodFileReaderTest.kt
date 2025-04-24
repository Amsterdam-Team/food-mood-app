package data

import org.junit.jupiter.api.Test
import com.google.common.truth.Truth.assertThat;
import logic.exception.FoodMoodException
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.assertThrows
import java.io.File

class CSVFoodFileReaderTest {

    var validFile = File("food-test.csv")
    var malformedFile = File("food-test-malformed.csv")
    val emptyFile = File("food-test-empty.csv")
    lateinit var reader: CSVFoodFileReader

  @Test
  fun `should not throw exception when reading valid formated csv file`() {
   // given

      reader = CSVFoodFileReader(validFile)
   // when & then
   assertDoesNotThrow{
    reader.readFile()
   }
  }

    @Test
    fun `should return all rows of file when reading valid formated csv file`() {
        // given
        reader = CSVFoodFileReader(validFile)
        // when
           val result =  reader.readFile().size
        //then
        assertThat(result).isEqualTo(ROWS_COUNT_FOOD_TEST_FILE)
    }

    @Test
    fun `should throw malformed csv file exception when reading malformed csv file`() {
        // given
        reader = CSVFoodFileReader(malformedFile)
        // when & then
        assertThrows<FoodMoodException.ParsingException.MalFormedCsvFileException> {
            reader.readFile()
        }
    }

    @Test
    fun `should throw empty file exception when reading empty csv file`() {
        // given

        reader = CSVFoodFileReader(emptyFile)
        // when & then
        assertThrows<FoodMoodException.ParsingException.EmptyFileException> {
            reader.readFile()
        }
    }

    companion object{
        const val ROWS_COUNT_FOOD_TEST_FILE = 2
    }
}