package logic.helpers

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
fun arribaBakedWinter() = createMeal(
    name = "arriba   baked winter squash mexican style",
    tags = listOf(
        "60-minutes-or-less",
        "time-to-make",
        "course",
        "main-ingredient",
        "cuisine",
        "north-american"
    )
)

fun allInTheKitchen() = createMeal(
    name = "all in the kitchen  chili",
    tags = listOf(
        "30-minutes-or-less",
        "time-to-make",
        "course",
        "main-ingredient",
        "cuisine",
        "north-american"
    )
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
