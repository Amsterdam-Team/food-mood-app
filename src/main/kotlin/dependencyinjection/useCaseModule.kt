package dependencyinjection

import logic.usecase.*
import logic.usecase.ingredient_game_usecases.StartIngredientGameUseCase
import logic.usecase.ingredient_game_usecases.SubmitAnswerUseCase
import org.koin.dsl.module

val useCaseModule = module {
    single { ExploreOtherCountriesUseCase(get(), get()) }
    single { GetFastHealthyMealsUseCase(get()) }
    single { GetIraqiMealsUseCase(get()) }
    single { GetItalianMealsForLargeGroupsUseCase(get()) }
    single { GetRandomKetoMealUseCase(get(), get()) }
    single { GetMealByNameUseCase(get(), get()) }
    single { GetMealsByAddedDateUseCase(get()) }
    single { GetRandomOneSweetMealWithoutEggsUseCase(get()) }
    single { GetSeafoodMealsByProteinUseCase(get()) }
    single { GuessPreparationTimeUseCase(get()) }
    single { SearchByCaloriesAndProteinUseCase(get()) }
    single { SearchByIngredientsUseCase(get()) }
    single { StartIngredientGameUseCase(get()) }
    single { SubmitAnswerUseCase() }
    single { SuggestAMealByCaloriesUseCase(get(), get()) }
    single { SuggestTop10EasyMealsUseCase(get()) }
}