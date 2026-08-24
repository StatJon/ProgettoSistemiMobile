package com.unibo.mobile.uicompose.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unibo.mobile.domain.models.Ability
import com.unibo.mobile.domain.models.ChallengeRating
import com.unibo.mobile.domain.models.CharacterEnemy
import com.unibo.mobile.domain.models.CharacterPlayer
import com.unibo.mobile.domain.models.CombatSnapshot
import com.unibo.mobile.domain.models.GamePhase
import com.unibo.mobile.domain.models.SaveGame
import com.unibo.mobile.domain.models.TurnPhase
import com.unibo.mobile.domain.usecases.api.FetchAbilityByClassNameUseCase
import com.unibo.mobile.domain.usecases.api.FetchAbilityByEnemyUseCase
import com.unibo.mobile.domain.usecases.api.FetchAbilityByNameUseCase
import com.unibo.mobile.domain.usecases.api.FetchEnemyByChallengeRatingUseCase
import com.unibo.mobile.domain.usecases.gamedata.GetAllPlayerClassesUseCase
import com.unibo.mobile.domain.usecases.gamedata.SetupDungeonUseCase
import com.unibo.mobile.domain.usecases.gamelogic.ApplyAbilityResultUseCase
import com.unibo.mobile.domain.usecases.gamelogic.CalculateAbilityUseCase
import com.unibo.mobile.domain.usecases.gamelogic.CheckCombatStatusUseCase
import com.unibo.mobile.domain.usecases.gamelogic.CheckpointUseCase
import com.unibo.mobile.domain.usecases.gamelogic.CombatLossUseCase
import com.unibo.mobile.domain.usecases.gamelogic.CombatWinUseCase
import com.unibo.mobile.domain.usecases.gamelogic.DecideEnemyAbilityUseCase
import com.unibo.mobile.domain.usecases.gamelogic.TurnCheckUseCase
import com.unibo.mobile.domain.usecases.savegame.LoadSaveGameUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class GameScreenViewModel(

    private val getAllPlayerClassesUseCase: GetAllPlayerClassesUseCase,
    private val loadSaveGameUseCase: LoadSaveGameUseCase,

    private val fetchAbilityByClassNameUseCase: FetchAbilityByClassNameUseCase,
    private val fetchAbilityByEnemyUseCase: FetchAbilityByEnemyUseCase,
    private val fetchAbilityByNameUseCase: FetchAbilityByNameUseCase,
    private val fetchEnemyByChallengeRatingUseCase: FetchEnemyByChallengeRatingUseCase,

    private val applyAbilityResultUseCase: ApplyAbilityResultUseCase,
    private val calculateAbilityUseCase: CalculateAbilityUseCase,
    private val checkCombatStatusUseCase: CheckCombatStatusUseCase,
    private val checkpointUseCase: CheckpointUseCase,
    private val combatLossUseCase: CombatLossUseCase,
    private val combatWinUseCase: CombatWinUseCase,
    private val decideEnemyAbilityUseCase: DecideEnemyAbilityUseCase,
    private val dungeonUseCase: SetupDungeonUseCase,
    private val turnCheckUseCase: TurnCheckUseCase

) : ViewModel() {

    // --- StateFlow

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _saveGame = MutableStateFlow(SaveGame(0, null))
    val saveGame: StateFlow<SaveGame> = _saveGame

    private val _dungeonIndex = MutableStateFlow(0)
    val dungeonIndex: StateFlow<Int> = _dungeonIndex

    private val _dungeonLength = MutableStateFlow(0)
    val dungeonLength: StateFlow<Int> = _dungeonLength

    private val _characterPlayer = MutableStateFlow<CharacterPlayer?>(null)
    val characterPlayer: StateFlow<CharacterPlayer?> = _characterPlayer

    private val _combatSnapshot = MutableStateFlow<CombatSnapshot?>(null)
    val combatSnapshot: StateFlow<CombatSnapshot?> = _combatSnapshot

    private val _turnPhase = MutableStateFlow(TurnPhase.PLAYER_TURN)
    val turnPhase: StateFlow<TurnPhase> = _turnPhase

    private val _gamePhase = MutableStateFlow(GamePhase.COMBAT)
    val gamePhase: StateFlow<GamePhase> = _gamePhase

    private val _lastActionMessage = MutableStateFlow<String?>(null)
    val lastActionMessage: StateFlow<String?> = _lastActionMessage

    // --- Init

    init {
        _isLoading.value = true
        viewModelScope.launch {
            loadSaveAndConstructPlayer()
            decideGamePhase()
        }
        _isLoading.value = false
    }

    // --- Public Functions

    // --- Orchestrator Private Functions

    private suspend fun decideGamePhase() {
        when {
            _dungeonIndex.value <= _dungeonLength.value -> { combatGameLoop() }
            // _dungeon.Index.value % _dungeonCheckpointStep.value == 0 -> { checkpointGameLoop } TODO non MVP
            _dungeonIndex.value > dungeonLength.value -> { endGame() }
        }
    }

    private suspend fun combatGameLoop() {
        _isLoading.value = true
        initCombat()
        _isLoading.value = false


    }

    // TODO: Implementare dopo primo collaudo, non MVP
    private fun checkpointGameLoop() {

    }

    private fun endGame() {

    }



    // --- Logic Private Functions

    private suspend fun loadSaveAndConstructPlayer() {
        val loadedSaveGame = loadSaveGameUseCase.invoke()
        _saveGame.value = loadedSaveGame
        _dungeonIndex.value = loadedSaveGame.saveSession!!.dungeon.dungeonIndex
        _dungeonLength.value = loadedSaveGame.saveSession!!.dungeon.dungeonLength
        _characterPlayer.value = loadedSaveGame.saveSession!!.playerCharacter
    }

    private suspend fun initCombat() {
        val enemy: CharacterEnemy = fetchEnemyByChallengeRatingUseCase.invoke(ChallengeRating.entries[_dungeonIndex.value])
        _combatSnapshot.value = CombatSnapshot(
            player = _characterPlayer.value!!,
            enemy = enemy,
            isPlayerTurn = true,
            isOver = false
        )
    }

    private fun combatLoop() {
        /*
        when (playerTurn){
            true -> playerTurn()
            false -> enemyTurn()

            TODO AGGIUNGERE CombatStatus IN CombatSnapshot


        when (combatStatus){
            ONGOING -> combatLoop()
            DEFEAT ->
            VICTORY ->
         */

    }



}




