package data

import CSVFoodParser

import com.google.common.truth.Truth.assertThat;
import data.helpers.ParseTestFactory
import data.helpers.createRowData
import kotlinx.datetime.LocalDate
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test

class CSVFoodParserTest{

 lateinit var csvFoodParser: CSVFoodParser

 @BeforeEach
 fun setUp() {
  csvFoodParser = CSVFoodParser()
 }

 @Test
 fun `should return null when the row data in file is empty`(){
  // given
  val row = emptyMap<String,String>()


  // when
  val result = csvFoodParser.parseRow(row)

  // then
  assertThat (result).isEqualTo(null)

 }

 @Test
 fun `should return valid meal when the row data is valid`(){
 // given
  val row = createRowData()

  // when
  val result = csvFoodParser.parseRow(row)

  // then
  assertThat(result).isNotNull()

 }

 @Test
 fun `should return meal with null description when the description data is empty`(){
  // given
  val row = createRowData(description = "").toMutableMap().apply {
   remove("description")
  }

  // when
  val result = csvFoodParser.parseRow(row)?.description

  // then
  assertThat(result).isNull()

 }

 @Test
 fun `should return null when name is null`() {
  // given
  val row = createRowData().toMutableMap().apply {
   remove("name")
  }

  // when
  val result = csvFoodParser.parseRow(row)

  // then
  assertThat(result?.name).isNull()
 }

 @Test
 fun `should return meal with null name field when the name in the data is empty`(){
  // given
  val row = createRowData(name= " ")

  // when
  val result = csvFoodParser.parseRow(row)?.name

  // then
  assertThat(result).isNull()

 }

 @Test
 fun `should return meal with null submitted date field when the submitted date in the data is null`(){
  // given
  val row = createRowData().toMutableMap().apply {
   remove("submitted")
  }

  // when
  val result = csvFoodParser.parseRow(row)?.submittedDate

  // then
  assertThat(result).isNull()

 }

 @Test
 fun `should return meal with null submitted date field when the submitted date in the data is mal formated`(){

  // given
  val row = createRowData(submittedDate = "2022.2.22")

  // when
  val result = csvFoodParser.parseRow(row)?.submittedDate

  // then
  assertThat(result).isNull()

 }

 @Test
 fun `should return meal with valid date field when the submitted date in the data is well formated`(){

  // given
  val row = createRowData(submittedDate = "2025-05-12")

  // when
  val result = csvFoodParser.parseRow(row)?.submittedDate

  // then
  assertThat(result).isEqualTo(LocalDate(2025, 5, 12))

 }

 @Test
 fun `should return null field tags when parsing empty string of list`(){
  // given
  val row = createRowData(tags = "")

  // when
  val result = csvFoodParser.parseRow(row)?.tags

  // then
  assertThat(result).isNull()

 }

 @Test
 fun `should return valid field tags when parsing valid string of list`(){
  // given
  val row = createRowData(tags = "['a','b']")

  // when
  val result = csvFoodParser.parseRow(row)?.tags

  // then
  assertThat(result).isEqualTo(listOf("a","b"))

 }

 @Test
 fun `should return null field nutrition when parsing empty string of list`(){

  // given
  val row = createRowData(nutrition = "")

  // when
  val result = csvFoodParser.parseRow(row)?.nutrition

  // then
  assertThat(result).isNull()

 }
 @Test
 fun `should return null field nutrition when parsing null nutrition value`(){

  // given
  val row = createRowData(nutrition = " ").toMutableMap().apply {
   remove("nutrition")
  }


  // when
  val result = csvFoodParser.parseRow(row)?.nutrition

  // then
  assertThat(result).isNull()

 }

 @Test
 fun `should return null field nutrition when parsing invalid string of list`(){
  // given
  val row = createRowData(nutrition = "['a','b']")

  // when
  val result = csvFoodParser.parseRow(row)?.nutrition

  // then
  assertThat(result).isNull()

 }


 @Test
 fun `should return valid nutrition field when parsing valid string of list`(){
  // given
  val row = createRowData(nutrition = "[0.3,0.1, 0.0, 1.2, 2.2, 0.01, 0.0]")

  // when
  val result = csvFoodParser.parseRow(row)?.nutrition

  // then
  assertThat(result).isEqualTo(ParseTestFactory.nutrition_sample)

 }


}