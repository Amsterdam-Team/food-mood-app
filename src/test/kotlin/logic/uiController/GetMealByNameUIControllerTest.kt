package logic.uiController

import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import logic.exception.FoodMoodException.Validation.NotFoundMealName
import logic.helpers.arribaBakedWinter
import logic.usecase.GetMealByNameUseCase
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import presentation.uiController.GetMealByNameUIController

class GetMealByNameUIControllerTest {
    private lateinit var useCase: GetMealByNameUseCase
    private lateinit var uiController : GetMealByNameUIController

    @BeforeEach
    fun setUp() {
        useCase = mockk(relaxed = true)
        uiController = GetMealByNameUIController(useCase)
    }

    @Test
    fun `should throw NotFoundMealName when name of meal not found`() {
        //given
        val mealName = ""
        every { useCase.getMealDetails(mealName) } throws NotFoundMealName

        //when && throw
        assertThrows<NotFoundMealName> {
            uiController.getMealByNameUI()
        }

    }

    @Test
    fun `should return details of meal when the name of meal is found`() {
        //given
        val mealName = "arriba   baked winter squash mexican style"
        val meal = listOf(
            arribaBakedWinter()
        )

        every { useCase.getMealDetails(mealName) }  returns meal

        //when
        uiController.getMealByNameUI()

        //then
        assertThat(meal).containsExactlyElementsIn(
            listOf(
                arribaBakedWinter()
            )
        )
    }
}