package com.unibo.mobile.uicompose.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.unibo.mobile.domain.di.UseCaseProvider
import com.unibo.mobile.domain.models.PlayerClass
import com.unibo.mobile.domain.usecases.GetAllPlayerClassesUseCase
import com.unibo.mobile.uicompose.common.BasicLabel
import com.unibo.mobile.uicompose.viewmodel.MainMenuViewModel
import com.unibo.mobile.uicompose.viewmodel.MainMenuViewModelFactory

@Composable
fun MainMenu(
    modifier: Modifier = Modifier,
    viewModel: MainMenuViewModel = viewModel(
        factory = MainMenuViewModelFactory(
            getAllPlayerClassesUseCase = UseCaseProvider.getAllPlayerClassesUseCase
        )
    )
) {

    val playerClassesList = viewModel.playerClassesList.collectAsStateWithLifecycle()

    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        BasicLabel(text = playerClassesList.value[0].name)

    }
}

//----- Preview -----//

@Preview(showBackground = true)
@Composable
fun MainMenuPreview() {
    val mockViewModel = remember { MainMenuViewModelMock() }
    MainMenu(viewModel = mockViewModel)
}

private fun MainMenuViewModelMock(): MainMenuViewModel {
    val mockUseCase = GetAllPlayerClassesUseCaseMock()
    return MainMenuViewModel(mockUseCase)
}

private class GetAllPlayerClassesUseCaseMock : GetAllPlayerClassesUseCase {
    override suspend fun invoke(): List<PlayerClass> {
        return listOf(
            PlayerClass(
                name = "Suor Mazzate",
                className = "cleric",
                baseHealthPoints = 12,
                baseManaPoints = 4,
                baseArmorClass = 14,
                baseAttackBonus = 2,
                healthGrowth = 8,
                manaGrowth = 2
            )
        )
    }
}
