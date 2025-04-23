package data

import org.junit.jupiter.api.Test
import com.google.common.truth.Truth.assertThat;
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import java.io.File

class CSVFoodFileReaderTest {

  lateinit var reader: CSVFoodFileReader

  @Test
  fun `should not throw exception when reading valid formated csv file`() {
   // given
      val file = File("food-test.csv")
      reader = CSVFoodFileReader(file)
   // when & then
   assertDoesNotThrow{
    reader.readFile()
   }
  }

    @Test
    fun `should return all rows of file when reading valid formated csv file`() {
        // given
        val file = File("food-test.csv")
        reader = CSVFoodFileReader(file)
        // when
           val result =  reader.readFile().size
        //then
        assertThat(result).isEqualTo(2)
    }

    @Test
    fun `should return empty rows when reading malformed csv file`() {
        // given
        val file = File("food-test-malformed.csv")
        reader = CSVFoodFileReader(file)
        // when
        val result = reader.readFile()
        //then
        assertThat(result.size).isEqualTo(0)
    }
}