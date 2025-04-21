package logic.usecase

import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import logic.MealsRepository
import logic.exception.FoodMoodException
import logic.helpers.createMeal
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GetItalianMealsForLargeGroupsUseCaseTest{
    private lateinit var mealsRepository: MealsRepository
    private lateinit var getItalianMealsForLargeGroupsUseCase: GetItalianMealsForLargeGroupsUseCase

    @BeforeEach
    fun setup(){
        mealsRepository = mockk(relaxed = true)
        getItalianMealsForLargeGroupsUseCase = GetItalianMealsForLargeGroupsUseCase(mealsRepository)
    }

    @Test
    fun `should throw empty data exception when given empty list`() {
        // Given
        every { mealsRepository.getAllMeals() } returns emptyList()

        // When && Then
        org.junit.jupiter.api.assertThrows<FoodMoodException.Validation.EmptyDataException> {
            getItalianMealsForLargeGroupsUseCase.getItalianMealsForLargeGroups()
        }
    }

    @Test
    fun `should return correct italian meals for large groups when given valid input`(){
        // Given
        every { mealsRepository.getAllMeals() } returns listOf(
            createMeal(
                name = "beef enchilada lasagna",
                description = "this tasty italian/mexican medley is delicious and easy to put together. a barely-modified, terrific sandi richards recipe that allows for you to prepare ingredients ahead of time to throw together once you get home. enjoy!",
                tags = listOf("time-to-make", "course", "main-ingredient", "cuisine", "main-dish", "beef", "pasta", "mexican", "easy", "european", "kid-friendly", "italian", "lasagna")
            ),
            createMeal(
                name = "chocolate  cherry pecan biscotti",
                description = "everybody i've ever made these for have loved them. i've made them with marshmellows before too... but that didn't really work out too well hehehe. in the picture the lighter biscottis don't have the cocoa powder in them and the darker ones do. (i like them without cocoa better but that's just me.)",
                tags = listOf("time-to-make", "course", "main-ingredient", "cuisine", "preparation", "for-large-groups", "italian")
            ),
            createMeal(
                name = "3 in 1 italian dip",
                description = "great idea here!  i found this in the kraft food and family holiday edition.  very simple and if some don't like the taste of an ingredient, they don't have to eat it because the 'dip' is sectioned off into 3 sections.",
                tags = listOf("north-american", "for-large-groups", "appetizers", "eggs-dairy", "vegetables")
            ),
            createMeal(
                name = "3 ingredient carnitas",
                description = "3 ingredients and a little patience are all you need to create flavorful carnitas.",
                tags = listOf("very-low-carbs", "summer", "dietary", "low-sodium", "seasonal")
            ),
        )

        // When
        val result = getItalianMealsForLargeGroupsUseCase.getItalianMealsForLargeGroups()

        // Then
        assertThat(result).containsExactly(
            createMeal(
                name = "chocolate  cherry pecan biscotti",
                description = "everybody i've ever made these for have loved them. i've made them with marshmellows before too... but that didn't really work out too well hehehe. in the picture the lighter biscottis don't have the cocoa powder in them and the darker ones do. (i like them without cocoa better but that's just me.)",
                tags = listOf("time-to-make", "course", "main-ingredient", "cuisine", "preparation", "for-large-groups", "italian")
            ),
            createMeal(
                name = "3 in 1 italian dip",
                description = "great idea here!  i found this in the kraft food and family holiday edition.  very simple and if some don't like the taste of an ingredient, they don't have to eat it because the 'dip' is sectioned off into 3 sections.",
                tags = listOf("north-american", "for-large-groups", "appetizers", "eggs-dairy", "vegetables")
            )
        )
    }

    @Test
    fun `should return correct italian meals for large groups without null names when given valid input`(){
        // Given
        every { mealsRepository.getAllMeals() } returns listOf(
            createMeal(
                name = "beef enchilada lasagna",
                description = "this tasty italian/mexican medley is delicious and easy to put together. a barely-modified, terrific sandi richards recipe that allows for you to prepare ingredients ahead of time to throw together once you get home. enjoy!",
                tags = listOf("time-to-make", "course", "main-ingredient", "cuisine", "main-dish", "beef", "pasta", "mexican", "easy", "european", "kid-friendly", "italian", "lasagna")
            ),
            createMeal(
                name = null,
                description = "everybody i've ever made these for have loved them. i've made them with marshmellows before too... but that didn't really work out too well hehehe. in the picture the lighter biscottis don't have the cocoa powder in them and the darker ones do. (i like them without cocoa better but that's just me.)",
                tags = listOf("time-to-make", "course", "main-ingredient", "cuisine", "preparation", "for-large-groups", "italian")
            ),
            createMeal(
                name = "3 in 1 italian dip",
                description = "great idea here!  i found this in the kraft food and family holiday edition.  very simple and if some don't like the taste of an ingredient, they don't have to eat it because the 'dip' is sectioned off into 3 sections.",
                tags = listOf("north-american", "for-large-groups", "appetizers", "eggs-dairy", "vegetables")
            ),
            createMeal(
                name = "3 ingredient carnitas",
                description = "3 ingredients and a little patience are all you need to create flavorful carnitas.",
                tags = listOf("very-low-carbs", "summer", "dietary", "low-sodium", "seasonal")
            ),
        )

        // When
        val result = getItalianMealsForLargeGroupsUseCase.getItalianMealsForLargeGroups()

        // Then
        assertThat(result).containsExactly(
            createMeal(
                name = "3 in 1 italian dip",
                description = "great idea here!  i found this in the kraft food and family holiday edition.  very simple and if some don't like the taste of an ingredient, they don't have to eat it because the 'dip' is sectioned off into 3 sections.",
                tags = listOf("north-american", "for-large-groups", "appetizers", "eggs-dairy", "vegetables")
            )
        )
    }

    @Test
    fun `should return correct italian meals for large groups without null descriptions when given valid input`(){
        // Given
        every { mealsRepository.getAllMeals() } returns listOf(
            createMeal(
                name = "beef enchilada lasagna",
                description = "this tasty italian/mexican medley is delicious and easy to put together. a barely-modified, terrific sandi richards recipe that allows for you to prepare ingredients ahead of time to throw together once you get home. enjoy!",
                tags = listOf("time-to-make", "course", "main-ingredient", "cuisine", "main-dish", "beef", "pasta", "mexican", "easy", "european", "kid-friendly", "italian", "lasagna")
            ),
            createMeal(
                name = "chocolate  cherry pecan biscotti",
                description = "everybody i've ever made these for have loved them. i've made them with marshmellows before too... but that didn't really work out too well hehehe. in the picture the lighter biscottis don't have the cocoa powder in them and the darker ones do. (i like them without cocoa better but that's just me.)",
                tags = listOf("time-to-make", "course", "main-ingredient", "cuisine", "preparation", "for-large-groups", "italian")
            ),
            createMeal(
                name = "3 in 1 italian dip",
                description = null,
                tags = listOf("north-american", "for-large-groups", "appetizers", "eggs-dairy", "vegetables")
            ),
            createMeal(
                name = "3 ingredient carnitas",
                description = "3 ingredients and a little patience are all you need to create flavorful carnitas.",
                tags = listOf("very-low-carbs", "summer", "dietary", "low-sodium", "seasonal")
            ),
        )

        // When
        val result = getItalianMealsForLargeGroupsUseCase.getItalianMealsForLargeGroups()

        // Then
        assertThat(result).containsExactly(
            createMeal(
                name = "chocolate  cherry pecan biscotti",
                description = "everybody i've ever made these for have loved them. i've made them with marshmellows before too... but that didn't really work out too well hehehe. in the picture the lighter biscottis don't have the cocoa powder in them and the darker ones do. (i like them without cocoa better but that's just me.)",
                tags = listOf("time-to-make", "course", "main-ingredient", "cuisine", "preparation", "for-large-groups", "italian")
            )
        )
    }

    @Test
    fun `should return correct italian meals for large groups without null tags when given valid input`(){
        // Given
        every { mealsRepository.getAllMeals() } returns listOf(
            createMeal(
                name = "beef enchilada lasagna",
                description = "this tasty italian/mexican medley is delicious and easy to put together. a barely-modified, terrific sandi richards recipe that allows for you to prepare ingredients ahead of time to throw together once you get home. enjoy!",
                tags = listOf("time-to-make", "course", "main-ingredient", "cuisine", "main-dish", "beef", "pasta", "mexican", "easy", "european", "kid-friendly", "italian", "lasagna")
            ),
            createMeal(
                name = "chocolate  cherry pecan biscotti",
                description = "everybody i've ever made these for have loved them. i've made them with marshmellows before too... but that didn't really work out too well hehehe. in the picture the lighter biscottis don't have the cocoa powder in them and the darker ones do. (i like them without cocoa better but that's just me.)",
                tags = listOf("time-to-make", "course", "main-ingredient", "cuisine", "preparation", "for-large-groups", "italian")
            ),
            createMeal(
                name = "3 in 1 italian dip",
                description = "great idea here!  i found this in the kraft food and family holiday edition.  very simple and if some don't like the taste of an ingredient, they don't have to eat it because the 'dip' is sectioned off into 3 sections.",
                tags = null
            ),
            createMeal(
                name = "3 ingredient carnitas",
                description = "3 ingredients and a little patience are all you need to create flavorful carnitas.",
                tags = listOf("very-low-carbs", "summer", "dietary", "low-sodium", "seasonal")
            ),
        )

        // When
        val result = getItalianMealsForLargeGroupsUseCase.getItalianMealsForLargeGroups()

        // Then
        assertThat(result).containsExactly(
            createMeal(
                name = "chocolate  cherry pecan biscotti",
                description = "everybody i've ever made these for have loved them. i've made them with marshmellows before too... but that didn't really work out too well hehehe. in the picture the lighter biscottis don't have the cocoa powder in them and the darker ones do. (i like them without cocoa better but that's just me.)",
                tags = listOf("time-to-make", "course", "main-ingredient", "cuisine", "preparation", "for-large-groups", "italian")
            )
        )
    }
}