package logic.helpers

import logic.models.Meal

object SuggestTop10EasyMealsMealTestFactory {

    fun validMeal(
        name: String = "Valid Meal",
        prepTime: Int = 25,
        ingredients: Int = 4,
        steps: Int = 5
    ): Meal {
        return createMeal(
            name = name,
            preparationTime = prepTime,
            numberOfIngredients = ingredients,
            numberOfSteps = steps
        )
    }


    fun mealWithNullPrepTime(): Meal = createMeal(
        name = "Meal With Null PrepTime",
        preparationTime = null,
        numberOfIngredients = 4,
        numberOfSteps = 5
    )

    fun mealWithNullIngredients(): Meal = createMeal(
        name = "Meal With Null Ingredients",
        preparationTime = 25,
        numberOfIngredients = null,
        numberOfSteps = 5
    )

    fun mealWithNullSteps(): Meal = createMeal(
        name = "Meal With Null Steps",
        preparationTime = 25,
        numberOfIngredients = 4,
        numberOfSteps = null
    )

    //





    fun createMultipleValidMeals(count: Int): List<Meal> {
        return (1..count).map {
            createMeal(
                name = "Step Heavy Meal",
                preparationTime = 20,
                numberOfIngredients = 4,
                numberOfSteps = 6
            )


        }
    }

    fun createMealsWithNullFields(count: Int): List<Meal> {
        return (1..count).map {
            createMeal(
                name = "Null Meal $it",
                preparationTime = null,
                numberOfIngredients = null,
                numberOfSteps = null
            )
        }
    }
    fun createValidMealsWithPreparationTime(count: Int, time: Int): List<Meal> {
        return (1..count).map {
            createMeal(
                name = "Meal $it",
                preparationTime = time,
                numberOfIngredients = 4,
                numberOfSteps = 5
            )
        }
    }
    fun createValidMealsWithIngredientsLimit(count: Int, ingredients: Int): List<Meal> {
        return (1..count).map {
            createMeal(
                name = "Meal $it",
                preparationTime = 20,
                numberOfIngredients = ingredients,
                numberOfSteps = 5
            )
        }
    }
    fun createValidMealsWithStepsLimit(count: Int, steps: Int): List<Meal> {
        return (1..count).map {
            createMeal(
                name = "Meal $it",
                preparationTime = 20,
                numberOfIngredients = 4,
                numberOfSteps = steps
            )
        }
    }
}





