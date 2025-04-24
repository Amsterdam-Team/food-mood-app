package logic.usecase

import com.google.common.truth.Truth.assertThat
import io.mockk.every
import io.mockk.mockk
import logic.MealsRepository
import logic.exception.FoodMoodException
import logic.helpers.*
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
            beefEnchilada(),
            chocolateCherryPecanBiscotti(),
            threeInOneItalianDip(),
            threeIngredientCarnitas(),
        )

        // When
        val result = getItalianMealsForLargeGroupsUseCase.getItalianMealsForLargeGroups()

        // Then
        assertThat(result).containsExactly(
            chocolateCherryPecanBiscotti(),
            threeInOneItalianDip()
        )
    }

    @Test
    fun `should return correct italian meals for large groups without null names when given valid input`(){
        // Given
        every { mealsRepository.getAllMeals() } returns listOf(
            beefEnchilada(),
            chocolateCherryPecanBiscotti().copy(name = null),
            threeInOneItalianDip(),
            threeIngredientCarnitas(),
        )

        // When
        val result = getItalianMealsForLargeGroupsUseCase.getItalianMealsForLargeGroups()

        // Then
        assertThat(result).containsExactly(
            threeInOneItalianDip()
        )
    }

    @Test
    fun `should return correct italian meals for large groups without null descriptions when given valid input`(){
        // Given
        every { mealsRepository.getAllMeals() } returns listOf(
            beefEnchilada(),
            chocolateCherryPecanBiscotti(),
            threeInOneItalianDip().copy(description = null),
            threeIngredientCarnitas(),
        )

        // When
        val result = getItalianMealsForLargeGroupsUseCase.getItalianMealsForLargeGroups()

        // Then
        assertThat(result).containsExactly(
            chocolateCherryPecanBiscotti()
        )
    }

    @Test
    fun `should return correct italian meals for large groups without null tags when given valid input`(){
        // Given
        every { mealsRepository.getAllMeals() } returns listOf(
            beefEnchilada(),
            chocolateCherryPecanBiscotti(),
            threeInOneItalianDip().copy(tags = null),
            threeIngredientCarnitas(),
        )

        // When
        val result = getItalianMealsForLargeGroupsUseCase.getItalianMealsForLargeGroups()

        // Then
        assertThat(result).containsExactly(
            chocolateCherryPecanBiscotti()
        )
    }
}