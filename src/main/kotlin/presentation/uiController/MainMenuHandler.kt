package presentation.uiController

import presentation.utils.withRedColor

class MainMenuHandler(
    private val featureControllers: Map<Int, BaseUIController>
) {
    fun start() {
        while (true) {
            showMainMenu()
            print("\nPlease enter a number (1–15) to choose a feature, or 0 to exit: ")
            val input = readlnOrNull()

            when (val choice = input?.toIntOrNull()) {
                in featureControllers.keys -> featureControllers[choice]?.execute()

                0 -> {
                    println("See You Later 🙂")
                    break
                }


                null -> println("❌ Invalid input. Please enter a valid number.".withRedColor())


                else -> println("❌ Unknown feature number.".withRedColor())
            }
        }
    }

    private fun showMainMenu() {
        println(
            """
        ================================
        🍽️  Food Change Mood - Main Menu
        ================================
        1. Get healthy fast food meals
        2. Search meals by name
        3. Identify Iraqi meals
        4. Easy food suggestions (game)
        5. Guess the preparation time (game)
        6. Sweets with no eggs
        7. Keto diet meal helper
        8. Search foods by add date
        9. Gym helper (by calories & protein)
        10. Explore other countries' food
        11. Ingredient game (guess ingredient)
        12. I Love Potato - show meals with potatoes
        13. So Thin Problem (700+ calorie meal)
        14. List seafood meals by protein (ranked)
        15. Large group Italian meal suggestion
        0. Exit
        """.trimIndent()
        )
    }

}
