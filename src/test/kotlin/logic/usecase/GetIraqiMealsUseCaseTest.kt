package logic.usecase

import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import logic.MealsRepository
import logic.exception.FoodMoodException
import logic.helpers.createMeal
import logic.helpers.bahrainiSweetRice
import logic.helpers.brownedEggplantWithYogurt
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class GetIraqiMealsUseCaseTest {

    private lateinit var getIraqiMealsUseCase: GetIraqiMealsUseCase
    private lateinit var mealsRepository: MealsRepository

    @BeforeEach
    fun setup() {
        mealsRepository = mockk(relaxed = true)
        getIraqiMealsUseCase = GetIraqiMealsUseCase(mealsRepository)
    }

    @Test
    fun `should throw empty data exception when given empty list`() {
        // Given
        every { mealsRepository.getAllMeals() } returns emptyList()

        // When && Then
        assertThrows<FoodMoodException.Validation.EmptyDataException> {
            getIraqiMealsUseCase.getOnlyIraqiMeals()
        }
    }

    @Test
    fun `should return correct iraqi meals when given valid input`() {
        // Given
        every { mealsRepository.getAllMeals() } returns listOf(
            bahrainiSweetRice(),
            createMeal(
                name = "barmia",
                description = "a one dish hearty meal using lamb, garlic and a tomato sauce served with rice and pitta.  i love this as it's so easy to prepare and cook and is really tasty.  it's from iraq i think--well my iraqi husband showed me how to cook it once and i have used it again and again as a supper or served at a long lunch with friends. i use two cutlets per person, but sometimes i use what bits and pieces i have in fridge/freezer such as the odd chop and shank. it's good to have bone in this dish as you cook it on the stove for a minimum 45 minutes and it gives it a rustic look, not to mention taste.",
                tags = listOf("main-ingredient", "cuisine")
            ),
            brownedEggplantWithYogurt(),
            createMeal(
                name = "beef cutlets",
                description = "this is my cutlet recipe. i add with a little grated coconut added for a twist sometimes. i like to serve these with a tomato sauce or a dipping sauce, as an accompaniment to pulao and raita or even dal and rice. not very sure of the yield as it varies depending on the size of my cutlets. i do not like monotony so the the sizes vary ;)",
                tags = listOf("60-minutes-or-less", "time-to-make", "course", "main-ingredient", "cuisine", "preparation", "side-dishes", "asian", "indian", "easy", "meat")
            )
            )

        // When
        val result = getIraqiMealsUseCase.getOnlyIraqiMeals()

        // Then
        assertThat(result).containsExactly(
            bahrainiSweetRice(),
            createMeal(
                name = "barmia",
                description = "a one dish hearty meal using lamb, garlic and a tomato sauce served with rice and pitta.  i love this as it's so easy to prepare and cook and is really tasty.  it's from iraq i think--well my iraqi husband showed me how to cook it once and i have used it again and again as a supper or served at a long lunch with friends. i use two cutlets per person, but sometimes i use what bits and pieces i have in fridge/freezer such as the odd chop and shank. it's good to have bone in this dish as you cook it on the stove for a minimum 45 minutes and it gives it a rustic look, not to mention taste.",
                tags = listOf("main-ingredient", "cuisine")
            ),
            brownedEggplantWithYogurt(),
        )
    }

    @Test
    fun `should return correct iraqi meals without null descriptions when given other valid input`() {
        // Given
        every { mealsRepository.getAllMeals() } returns listOf(
            bahrainiSweetRice(),
            createMeal(
                name = "barmia",
                description = null,
                tags = listOf("main-ingredient", "cuisine")
            ),
            brownedEggplantWithYogurt(),
            createMeal(
                name = "beef cutlets",
                description = "this is my cutlet recipe. i add with a little grated coconut added for a twist sometimes. i like to serve these with a tomato sauce or a dipping sauce, as an accompaniment to pulao and raita or even dal and rice. not very sure of the yield as it varies depending on the size of my cutlets. i do not like monotony so the the sizes vary ;)",
                tags = listOf("60-minutes-or-less", "time-to-make", "course", "main-ingredient", "cuisine", "preparation", "side-dishes", "asian", "indian", "easy", "meat")
            )
        )

        // When
        val result = getIraqiMealsUseCase.getOnlyIraqiMeals()

        // Then
        assertThat(result).containsExactly(
            bahrainiSweetRice(),
            brownedEggplantWithYogurt(),
        )
    }

    @Test
    fun `should return correct iraqi meals without null tags when given other valid input`() {
        // Given
        every { mealsRepository.getAllMeals() } returns listOf(
            createMeal(
                name = "bahraini sweet rice  muhammar",
                description = "modified from the complete middle east cookbook by tess mallos. this is a beautiful accompaniment to recipe #372093.",
                tags = null
            ),
            createMeal(
                name = "barmia",
                description = "a one dish hearty meal using lamb, garlic and a tomato sauce served with rice and pitta.  i love this as it's so easy to prepare and cook and is really tasty.  it's from iraq i think--well my iraqi husband showed me how to cook it once and i have used it again and again as a supper or served at a long lunch with friends. i use two cutlets per person, but sometimes i use what bits and pieces i have in fridge/freezer such as the odd chop and shank. it's good to have bone in this dish as you cook it on the stove for a minimum 45 minutes and it gives it a rustic look, not to mention taste.",
                tags = listOf("main-ingredient", "cuisine")
            ),
            brownedEggplantWithYogurt(),
            createMeal(
                name = "beef cutlets",
                description = "this is my cutlet recipe. i add with a little grated coconut added for a twist sometimes. i like to serve these with a tomato sauce or a dipping sauce, as an accompaniment to pulao and raita or even dal and rice. not very sure of the yield as it varies depending on the size of my cutlets. i do not like monotony so the the sizes vary ;)",
                tags = listOf("60-minutes-or-less", "time-to-make", "course", "main-ingredient", "cuisine", "preparation", "side-dishes", "asian", "indian", "easy", "meat")
            )
        )

        // When
        val result = getIraqiMealsUseCase.getOnlyIraqiMeals()

        // Then
        assertThat(result).containsExactly(
            createMeal(
                name = "barmia",
                description = "a one dish hearty meal using lamb, garlic and a tomato sauce served with rice and pitta.  i love this as it's so easy to prepare and cook and is really tasty.  it's from iraq i think--well my iraqi husband showed me how to cook it once and i have used it again and again as a supper or served at a long lunch with friends. i use two cutlets per person, but sometimes i use what bits and pieces i have in fridge/freezer such as the odd chop and shank. it's good to have bone in this dish as you cook it on the stove for a minimum 45 minutes and it gives it a rustic look, not to mention taste.",
                tags = listOf("main-ingredient", "cuisine")
            ),
            brownedEggplantWithYogurt(),
        )
    }
}