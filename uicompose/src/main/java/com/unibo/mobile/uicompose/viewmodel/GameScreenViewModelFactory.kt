package com.unibo.mobile.uicompose.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.unibo.mobile.domain.usecases.gamelogic.CalculateAbilityResultUseCase

class GameScreenViewModelFactory(
    private val calculateAbilityResultUseCase: CalculateAbilityResultUseCase,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GameScreenViewModel::class.java)) {
            return GameScreenViewModel(
                getAllPlayerClassesUseCase = TODO(),
                loadSaveGameUseCase = TODO(),
                fetchAbilityByClassNameUseCase = TODO(),
                fetchAbilityByEnemyUseCase = TODO(),
                fetchAbilityByNameUseCase = TODO(),
                fetchEnemyByChallengeRatingUseCase = TODO(),
                applyAbilityResultUseCase = TODO(),
                applyPlayerAbilityCostUseCase = TODO(),
                calculateAbilityResultUseCase = calculateAbilityResultUseCase,
                checkCombatStatusUseCase = TODO(),
                checkpointUseCase = TODO(),
                combatLossUseCase = TODO(),
                combatWinUseCase = TODO(),
                decideEnemyAbilityUseCase = TODO(),
                determineGamePhaseUseCase = TODO(),
                dungeonUseCase = TODO(),
                turnCheckUseCase = TODO()
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}