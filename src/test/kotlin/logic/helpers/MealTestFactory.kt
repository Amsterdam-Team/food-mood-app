package logic.helpers

import logic.models.Nutrition

fun bahrainiSweetRice() =
    createMeal(
        name = "bahraini sweet rice  muhammar",
        description = "modified from the complete middle east cookbook by tess mallos. this is a beautiful accompaniment to recipe #372093.",
        tags = listOf("iraqi", "main-ingredient", "cuisine")
    )

fun brownedEggplantWithYogurt() = createMeal(
    name = "browned eggplant  aubergine  with yogurt",
    description = "pairing fried eggplant with yogurt and garlic is one of the favorite ways of serving eggplant in iraq. it is a simple and beautiful dish, great for hot summer days.",
    tags = listOf("main-ingredient", "cuisine", "iraqi")
)

fun chocolateCherryPecanBiscotti() = createMeal(
    name = "chocolate  cherry pecan biscotti",
    description = "everybody i've ever made these for have loved them. i've made them with marshmellows before too... but that didn't really work out too well hehehe. in the picture the lighter biscottis don't have the cocoa powder in them and the darker ones do. (i like them without cocoa better but that's just me.)",
    tags = listOf("time-to-make", "course", "main-ingredient", "cuisine", "preparation", "for-large-groups", "italian")
)

fun threeInOneItalianDip() = createMeal(
    name = "3 in 1 italian dip",
    description = "great idea here!  i found this in the kraft food and family holiday edition.  very simple and if some don't like the taste of an ingredient, they don't have to eat it because the 'dip' is sectioned off into 3 sections.",
    tags = listOf("north-american", "for-large-groups", "appetizers", "eggs-dairy", "vegetables")
)

fun eggAvocadoBowl() = createMeal(
    name = "Egg Avocado Bowl",
    description = "A delicious keto bowl with eggs, avocado, and bacon.",
    nutrition = Nutrition(
        calories = 450.0,
        totalFat = 35.0,
        sugar = 1.0,
        sodium = 500.0,
        protein = 25.0,
        saturatedFat = 10.0,
        carbohydrates = 4.0
    )
)

fun grilledChickenSalad() = createMeal(
    name = "Grilled Chicken Salad",
    description = "Grilled Chicken",
    nutrition = Nutrition(
        calories = 400.0,
        totalFat = 30.0,
        sugar = 2.0,
        sodium = 600.0,
        protein = 30.0,
        saturatedFat = 8.0,
        carbohydrates = 6.0
    )
)

fun zucchiniNoodlesWithPesto() = createMeal(
    name = "Zucchini Noodles with Pesto",
    description = "Zucchini noodles with a creamy pesto sauce.",
    nutrition = Nutrition(
        calories = 350.0,
        totalFat = 28.0,
        sugar = 3.0,
        sodium = 700.0,
        protein = 15.0,
        saturatedFat = 6.0,
        carbohydrates = 8.0
    )
)

fun beefEnchilada() = createMeal(
    name = "beef enchilada lasagna",
    description = "this tasty italian/mexican medley is delicious and easy to put together. a barely-modified, terrific sandi richards recipe that allows for you to prepare ingredients ahead of time to throw together once you get home. enjoy!",
    tags = listOf("time-to-make", "course", "main-ingredient", "cuisine", "main-dish", "beef", "pasta", "mexican", "easy", "european", "kid-friendly", "italian", "lasagna")
)

fun threeIngredientCarnitas() = createMeal(
    name = "3 ingredient carnitas",
    description = "3 ingredients and a little patience are all you need to create flavorful carnitas.",
    tags = listOf("very-low-carbs", "summer", "dietary", "low-sodium", "seasonal")
)

fun barmia() = createMeal(
    name = "barmia",
    description = "a one dish hearty meal using lamb, garlic and a tomato sauce served with rice and pitta.  i love this as it's so easy to prepare and cook and is really tasty.  it's from iraq i think--well my iraqi husband showed me how to cook it once and i have used it again and again as a supper or served at a long lunch with friends. i use two cutlets per person, but sometimes i use what bits and pieces i have in fridge/freezer such as the odd chop and shank. it's good to have bone in this dish as you cook it on the stove for a minimum 45 minutes and it gives it a rustic look, not to mention taste.",
    tags = listOf("main-ingredient", "cuisine")
)

fun beefCutlets() = createMeal(
    name = "beef cutlets",
    description = "this is my cutlet recipe. i add with a little grated coconut added for a twist sometimes. i like to serve these with a tomato sauce or a dipping sauce, as an accompaniment to pulao and raita or even dal and rice. not very sure of the yield as it varies depending on the size of my cutlets. i do not like monotony so the the sizes vary ;)",
    tags = listOf("60-minutes-or-less", "time-to-make", "course", "main-ingredient", "cuisine", "preparation", "side-dishes", "asian", "indian", "easy", "meat")
)
