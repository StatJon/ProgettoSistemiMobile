package com.unibo.mobile.uicompose.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.unibo.mobile.domain.usecases.api.FetchEnemyByChallengeRatingUseCase
import com.unibo.mobile.domain.usecases.gamedata.ValidateDungeonLengthUseCase
import com.unibo.mobile.domain.usecases.gamelogic.ApplyAbilityResultUseCase
import com.unibo.mobile.domain.usecases.gamelogic.ApplyPlayerAbilityCostUseCase
import com.unibo.mobile.domain.usecases.gamelogic.CalculateAbilityResultUseCase
import com.unibo.mobile.domain.usecases.gamelogic.CheckCombatStatusUseCase
import com.unibo.mobile.domain.usecases.gamelogic.CheckpointUseCase
import com.unibo.mobile.domain.usecases.gamelogic.DecideEnemyAbilityUseCase
import com.unibo.mobile.domain.usecases.gamelogic.DetermineChallengeRatingUseCase
import com.unibo.mobile.domain.usecases.gamelogic.DetermineGamePhaseUseCase
import com.unibo.mobile.domain.usecases.gamelogic.LevelUpUseCase
import com.unibo.mobile.domain.usecases.savegame.LoadSaveGameUseCase
import com.unibo.mobile.domain.usecases.savegame.SaveSaveGameUseCase

class GameScreenViewModelFactory(
    private val applyAbilityResultUseCase: ApplyAbilityResultUseCase,
    private val applyPlayerAbilityCostUseCase: ApplyPlayerAbilityCostUseCase,
    private val calculateAbilityResultUseCase: CalculateAbilityResultUseCase,
    private val checkCombatStatusUseCase: CheckCombatStatusUseCase,
    private val decideEnemyAbilityUseCase: DecideEnemyAbilityUseCase,
    private val determineChallengeRatingUseCase: DetermineChallengeRatingUseCase,
    private val determineGamePhaseUseCase: DetermineGamePhaseUseCase,
    private val fetchEnemyByChallengeRatingUseCase: FetchEnemyByChallengeRatingUseCase,
    private val loadSaveGameUseCase: LoadSaveGameUseCase,
    private val levelUpUseCase: LevelUpUseCase,
    private val checkpointUseCase: CheckpointUseCase,
    private val saveSaveGameUseCase: SaveSaveGameUseCase,
    private val validateDungeonLengthUseCase: ValidateDungeonLengthUseCase,

) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GameScreenViewModel::class.java)) {
            return GameScreenViewModel(
                loadSaveGameUseCase = loadSaveGameUseCase,
                saveSaveGameUseCase = saveSaveGameUseCase,
                fetchEnemyByChallengeRatingUseCase = fetchEnemyByChallengeRatingUseCase,
                applyAbilityResultUseCase = applyAbilityResultUseCase,
                applyPlayerAbilityCostUseCase = applyPlayerAbilityCostUseCase,
                calculateAbilityResultUseCase = calculateAbilityResultUseCase,
                checkCombatStatusUseCase = checkCombatStatusUseCase,
                decideEnemyAbilityUseCase = decideEnemyAbilityUseCase,
                determineChallengeRatingUseCase = determineChallengeRatingUseCase,
                determineGamePhaseUseCase = determineGamePhaseUseCase,
                levelUpUseCase = levelUpUseCase,
                checkpointUseCase = checkpointUseCase,
                validateDungeonLengthUseCase = validateDungeonLengthUseCase,
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}