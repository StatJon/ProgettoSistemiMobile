package com.unibo.mobile.uicompose.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.unibo.mobile.domain.usecases.api.FetchAbilityByNameUseCase
import com.unibo.mobile.domain.usecases.api.FetchEnemyByChallengeRatingUseCase
import com.unibo.mobile.domain.usecases.gamelogic.CalculateAbilityResultUseCase
import com.unibo.mobile.domain.usecases.gamelogic.DetermineChallengeRatingUseCase
import com.unibo.mobile.domain.usecases.gamelogic.DetermineGamePhaseUseCase

class GameScreenViewModelFactory(
    private val calculateAbilityResultUseCase: CalculateAbilityResultUseCase,
    private val fetchEnemyByChallengeRatingUseCase: FetchEnemyByChallengeRatingUseCase,
    private val fetchAbilityByNameUseCase: FetchAbilityByNameUseCase,
    private val determineGamePhaseUseCase: DetermineGamePhaseUseCase,
    private val determineChallengeRatingUseCase: DetermineChallengeRatingUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GameScreenViewModel::class.java)) {
            return GameScreenViewModel(
                getAllPlayerClassesUseCase = TODO(),
                loadSaveGameUseCase = TODO(),
                fetchAbilityByClassNameUseCase = TODO(),
                fetchAbilityByEnemyUseCase = TODO(),
                fetchAbilityByNameUseCase = fetchAbilityByNameUseCase,
                fetchEnemyByChallengeRatingUseCase = fetchEnemyByChallengeRatingUseCase,
                applyAbilityResultUseCase = TODO(),
                applyPlayerAbilityCostUseCase = TODO(),
                calculateAbilityResultUseCase = calculateAbilityResultUseCase,
                checkCombatStatusUseCase = TODO(),
                checkpointUseCase = TODO(),
                combatLossUseCase = TODO(),
                combatWinUseCase = TODO(),
                decideEnemyAbilityUseCase = TODO(),
                determineGamePhaseUseCase = determineGamePhaseUseCase,
                dungeonUseCase = TODO(),
                turnCheckUseCase = TODO(),
                determineChallengeRatingUseCase = determineChallengeRatingUseCase
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}