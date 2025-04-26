package logic.usecase.ingredient_game_usecases

import com.google.common.truth.Truth.assertThat
import io.mockk.mockk
import logic.MealsRepository
import logic.exception.FoodMoodException
import org.junit.Assert.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import presentation.models.IngredientGameResult
import presentation.uiController.helpers.IngredientMealsTestFactory

class StartIngredientGameUseCaseTest{

 private lateinit var mealsRepository : MealsRepository
 private lateinit var ingredientGameUseCase: StartIngredientGameUseCase



 @BeforeEach
 fun setup(){
  mealsRepository = mockk(relaxed = true)
  ingredientGameUseCase = StartIngredientGameUseCase(mealsRepository)
 }

 @Test
 fun `should return GameFinished from IngredientGameResult sealed class when game is finished`(){
  // Given
  val gameState = IngredientMealsTestFactory.finishedGameState()

  //when
  val result = ingredientGameUseCase.startGame(gameState)

  //then
  assertThat(result).isInstanceOf(IngredientGameResult.GameFinished::class.java)

 }

 @Test
 fun `should return GameInProgress with valid meal and distractors`() {
  // Given
  val gameState = IngredientMealsTestFactory.newGameState()
  val useCase = StartIngredientGameUseCase(IngredientMealsTestFactory.withValidData())

  // When
  val result = useCase.startGame(gameState)

  // Then
  assertThat(result).isInstanceOf(IngredientGameResult.GameInProgress::class.java)
  val gameResult = result as IngredientGameResult.GameInProgress
  assertThat(gameResult.ingredientOptions).contains(gameResult.correctIngredient)
  assertThat(gameResult.ingredientOptions.size).isEqualTo(3)
 }

 @Test
 fun `should throw exception when no meals are available`(){
  // Given
  val gameState = IngredientMealsTestFactory.newGameState()
  val ingredientGameUseCase = StartIngredientGameUseCase(IngredientMealsTestFactory.empty())

  // when && then
  assertThrows(FoodMoodException.GameException.NoValidMealsAvailable::class.java){
   ingredientGameUseCase.startGame(gameState)
  }
 }

 @Test
 fun `should throw exception when distractors less than 2`() {
  // Given
  val gameState = IngredientMealsTestFactory.newGameState()
  val useCase = StartIngredientGameUseCase(IngredientMealsTestFactory.withLessDistractors())

  // Then
  assertThrows(FoodMoodException.GameException.IngredientOptionsNotEnough::class.java) {
   // When
   useCase.startGame(gameState)
  }
 }

 @Test
 fun `should always include correct ingredient in options`() {
  // Given
  val gameState = IngredientMealsTestFactory.newGameState()
  val useCase = StartIngredientGameUseCase(IngredientMealsTestFactory.withValidData())

  val result = useCase.startGame(gameState) as IngredientGameResult.GameInProgress

  // Then
  assertThat(result.ingredientOptions).contains(result.correctIngredient)
 }


 @Test
 fun `should throw exception when meal has no ingredients`(){
  // Given
  val gameState = IngredientMealsTestFactory.newGameState()
  val ingredientGameUseCase = StartIngredientGameUseCase(IngredientMealsTestFactory.withNoIngredients())

  // every { mealsRepository.getAllMeals()} returns emptyList()

  // when && then
  assertThrows(FoodMoodException.GameException.InvalidIngredientData::class.java){
   ingredientGameUseCase.startGame(gameState)
  }
 }

 @Test
 fun `should throw exception when we have ingredients with unexpected format`(){
  // Given
  val gameState = IngredientMealsTestFactory.newGameState()
  val ingredientGameUseCase = StartIngredientGameUseCase(IngredientMealsTestFactory.withInvalidIngredientsFormat())

  // when && then
  assertThrows(FoodMoodException.GameException.InvalidIngredientData::class.java){
   ingredientGameUseCase.startGame(gameState)
  }
 }
 @Test
 fun `should return correct ingredient when selecting ingredient from the list of ingredients for the selected meal`(){
  // Given
  val gameState = IngredientMealsTestFactory.newGameState()
  val ingredientGameUseCase = StartIngredientGameUseCase(IngredientMealsTestFactory.withCorrectIngredient())

  // when
  val result = ingredientGameUseCase.startGame(gameState)

  assertThat(result).isInstanceOf(IngredientGameResult.GameInProgress::class.java)

  val gameInProgress = result as IngredientGameResult.GameInProgress
  // then
  assertThat(gameInProgress.ingredientOptions).contains(gameInProgress.correctIngredient)

  println("${gameInProgress.ingredientOptions} + ${gameInProgress.correctIngredient}")
 }

 @Test
 fun `should return non-repeated meals when playing 15 consecutive game rounds`() {
  // Given
  val meals = IngredientMealsTestFactory.generateMeals(count = 20)
  val useCase = StartIngredientGameUseCase(IngredientMealsTestFactory.withMeals(meals))
  val gameState = IngredientMealsTestFactory.newGameState()

  val selectedMealIds = mutableSetOf<String>()

  // When
  repeat(15) {
   val result = useCase.startGame(gameState)
   val game = result as IngredientGameResult.GameInProgress

   // Collect meal id
   val mealId = meals.find { it.name == game.mealName }?.id
   assertThat(mealId).isNotNull()
   assertThat(selectedMealIds).doesNotContain(mealId)

   selectedMealIds.add(mealId!!)
  }

  // Then
  assertThat(selectedMealIds.size).isEqualTo(15)
  assertThat(gameState.usedMealIds).containsExactlyElementsIn(selectedMealIds)
 }

 @Test
 fun `should throw exception when random ingredient is blank`() {
  // Given
  val gameState = IngredientMealsTestFactory.newGameState()
  val ingredientGameUseCase = StartIngredientGameUseCase(IngredientMealsTestFactory.withBlankIngredient())

  // When + Then
  assertThrows(FoodMoodException.GameException.InvalidIngredientData::class.java) {
   ingredientGameUseCase.startGame(gameState)
  }
 }

}