package com.unibo.mobile.uicompose.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.unibo.mobile.domain.di.UseCaseProvider
import com.unibo.mobile.uicompose.common.BasicButton
import com.unibo.mobile.uicompose.common.BasicLabel
import com.unibo.mobile.uicompose.viewmodel.MainMenuViewModel
import com.unibo.mobile.uicompose.viewmodel.MainMenuViewModelFactory

@Composable
fun MainMenu(
    modifier: Modifier = Modifier
) {
    val viewModel: MainMenuViewModel =
        viewModel(
            factory = MainMenuViewModelFactory(
                getAllPlayerClassesUseCase = UseCaseProvider.getAllPlayerClassesUseCase
            )
        )

    val playerClassesList = viewModel.playerClassesList.collectAsStateWithLifecycle()

    Box(
        modifier = modifier
    ) {
        BasicLabel(text = playerClassesList.value[0].name)

    }
}