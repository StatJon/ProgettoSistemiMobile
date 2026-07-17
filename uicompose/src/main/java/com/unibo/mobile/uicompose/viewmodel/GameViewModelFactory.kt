package com.unibo.mobile.uicompose.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.unibo.mobile.domain.di.UseCasesProvider
import com.unibo.mobile.domain.usecases.DungeonUseCase
import com.unibo.mobile.domain.usecases.RoomSafeUseCase
import com.unibo.mobile.domain.usecases.SaveUseCase

class GameViewModelFactory(
    private val useCasesProvider: UseCasesProvider
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return GameViewModel(
            staticDataUseCase = useCasesProvider.staticDataUseCase,
            saveUseCase = useCasesProvider.saveUseCase,
            dungeonUseCase = useCasesProvider.dungeonUseCase,
            roomSafeUseCase = useCasesProvider.roomSafeUseCase,
            roomCombatUseCase = useCasesProvider.roomCombatUseCase
        ) as T
    }
}