package data

import CSVFoodParser
import data.helpers.createRowData
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify

import junit.framework.TestCase.assertTrue
import logic.MealsRepository

import kotlin.test.BeforeTest
import kotlin.test.Test


class MealsRepositoryImplTest{
  lateinit var mealsRepository : MealsRepository
    lateinit var fileReader : CSVFoodFileReader
    lateinit var csvFoodParser : CSVFoodParser

    @BeforeTest
    fun setup(){
        csvFoodParser = mockk(relaxed = true)
        fileReader = mockk(relaxed = true)
    }

 @Test
 fun `should start reading file when getting meals `(){
     fileReader = mockk(relaxed = true)
     csvFoodParser =mockk(relaxed = true)
     mealsRepository = MealsRepositoryImpl(csvFoodParser, fileReader)
     mealsRepository.getAllMeals()
     verify(exactly = 1) { fileReader.readFile() }
 }
    @Test
    fun `should parse all rows returned by file reader`(){
        // given
        val rows = listOf(createRowData(), createRowData())
        every { fileReader.readFile() } returns rows
        every {csvFoodParser.parseRow(any())} returns mockk()
        MealsRepositoryImpl(csvFoodParser, fileReader)

        verify (exactly = 1) { fileReader.readFile() }
        verify(exactly = rows.size) { csvFoodParser.parseRow(any()) }
    }

    @Test
    fun `should ignore null values returned from parser`() {
        fileReader = mockk()
        csvFoodParser = mockk()

        every { fileReader.readFile() } returns listOf(createRowData(), createRowData())
        every { csvFoodParser.parseRow(any()) } returns null

        mealsRepository = MealsRepositoryImpl(csvFoodParser, fileReader)

        val result = mealsRepository.getAllMeals()

        assertTrue(result.isEmpty())
    }

 }