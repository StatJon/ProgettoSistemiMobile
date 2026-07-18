package com.unibo.mobile.uicompose.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unibo.mobile.domain.model.ability.Ability
import com.unibo.mobile.domain.model.combat.CombatOutcome
import com.unibo.mobile.domain.model.combat.CombatState
import com.unibo.mobile.domain.model.dungeon.Dungeon
import com.unibo.mobile.domain.model.dungeon.RoomAction
import com.unibo.mobile.domain.model.dungeon.RoomTypeCombat
import com.unibo.mobile.domain.model.dungeon.RoomTypeSafe
import com.unibo.mobile.domain.model.entity.CombatEntity
import com.unibo.mobile.domain.model.entity.PlayerClass
import com.unibo.mobile.domain.model.save.SaveGame
import com.unibo.mobile.domain.usecases.DungeonUseCase
import com.unibo.mobile.domain.usecases.RoomCombatUseCase
import com.unibo.mobile.domain.usecases.RoomSafeUseCase
import com.unibo.mobile.domain.usecases.SaveUseCase
import com.unibo.mobile.domain.usecases.StaticDataUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

class GameViewModel(
    private val staticDataUseCase: StaticDataUseCase,
    private val saveUseCase: SaveUseCase,
    private val dungeonUseCase: DungeonUseCase,
    private val roomSafeUseCase: RoomSafeUseCase,
    private val roomCombatUseCase: RoomCombatUseCase
) : ViewModel() {
    private val _state = MutableStateFlow<GameState>(GameState.Loading)
    val state: StateFlow<GameState> = _state.asStateFlow()
    private lateinit var saveGame: SaveGame
    private lateinit var dungeon: Dungeon
    private var currentCombat: CombatState? = null
    init {
        viewModelScope.launch {
            loadMainMenu()
        }
    }

    private suspend fun loadMainMenu() {
        saveGame = saveUseCase.loadGame()
        val playerClasses = staticDataUseCase.getPlayerClasses()
        _state.value = GameState.MainMenu(
            winCounter = saveGame.winCounter,
            playerClasses = playerClasses,
            isContinuePossible = saveGame.saveSession != null
        )
    }

    // --- Callback MainMenu (stub: logica reale da collegare in seguito) ---
    fun onNewGame(playerClass: PlayerClass) {
        viewModelScope.launch {
            val newCharacter = dungeonUseCase.createNewPlayerCharacter(playerClass)
            saveGame = saveUseCase.newSession(newCharacter)
            startSession()
        }
    }

    fun onContinue() {
        if (saveGame.saveSession == null) return
        viewModelScope.launch {
            startSession()
        }
    }

    // --- Sessione di gioco (comune a Safe/Combat/End) ---
    private suspend fun startSession() {
        dungeon = dungeonUseCase.initDungeon(
            winCounter = saveGame.winCounter,
            savedDungeon = null
        )
        renderCurrentRoom()
    }

    private suspend fun renderCurrentRoom() {
        val session = saveGame.saveSession ?: return
        val currentRoomIndex = session.currentRoomIndex

        if (dungeonUseCase.checkVictory(dungeon, currentRoomIndex)) {
            endRun(isWon = true) // dungeon completato
            return
        }

        val currentRoomType = dungeon.rooms[currentRoomIndex].roomType
        _state.value = if (currentRoomType is RoomTypeSafe) {
            saveUseCase.saveGameProgress(saveGame) // salvataggio automatico su stanza safe
            GameState.RoomSafe(
                player = session.playerCharacter,
                availableActions = currentRoomType.availableActions
            )
        } else {
            val combat = roomCombatUseCase.startCombat(
                playerCharacter = session.playerCharacter,
                room = currentRoomType as RoomTypeCombat
            )
            currentCombat = combat
            GameState.RoomCombat(
                combatState = combat,
                playerCharacter = session.playerCharacter
            )
        }
    }

    // --- Callback RoomCombat ---
    fun onAbilityAndEntityConfirmed(ability: Ability, target: CombatEntity) {
        val combat = currentCombat ?: return
        viewModelScope.launch {
            val sequence = roomCombatUseCase.playerTurn(combat, ability, target)
            if (sequence.isEmpty()) return@launch

            val session = saveGame.saveSession
            for (combatState in sequence) {
                currentCombat = combatState
                if (session != null) {
                    _state.value = GameState.RoomCombat(
                        combatState = combatState,
                        playerCharacter = session.playerCharacter
                    )
                }
                delay(ACTION_DELAY_MS)
            }

            when (sequence.last().status) {
                CombatOutcome.VICTORY -> onCombatVictory(sequence.last())
                CombatOutcome.DEFEAT -> onCombatDefeat()
                CombatOutcome.ONGOING -> Unit
            }
        }
    }

    private suspend fun onCombatVictory(finalState: CombatState) {
        val session = saveGame.saveSession ?: return
        val enemies = finalState.getAllEnemies()
        val reward = roomCombatUseCase.calculatePostCombatReward(enemies)
        val rewardExp = enemies.sumOf { it.rewardExp }
        val updatedCharacter = roomCombatUseCase
            .updatePlayerCharacter(session.playerCharacter, finalState.getAllAllies(), reward)
            .addExp(rewardExp)
        val nextSession = session
            .updatePlayerCharacter(updatedCharacter)
            .updateRoomIndex(session.currentRoomIndex + 1)
        saveGame = saveGame.updateSession(nextSession)
        currentCombat = null
        renderCurrentRoom()
    }

    private suspend fun onCombatDefeat() {
        endRun(isWon = false)
    }

    // --- Fine run (vittoria dungeon o sconfitta): chiude la sessione e mostra EndScreen ---
    private suspend fun endRun(isWon: Boolean) {
        val endedSaveGame = if (isWon) saveGame.incrementWinCounter() else saveGame
        saveGame = endedSaveGame.updateSession(null)
        saveUseCase.saveGameProgress(saveGame)
        currentCombat = null
        _state.value = GameState.EndScreen(isWon = isWon)
    }

    // --- Callback EndScreen ---
    fun onBackToMenu() {
        viewModelScope.launch {
            loadMainMenu()
        }
    }

    // --- Callback RoomSafe ---
    fun onRoomAction(roomAction: RoomAction) {
        val session = saveGame.saveSession ?: return
        viewModelScope.launch {
            val updatedSession = roomSafeUseCase.applyRoomAction(session, roomAction)
            saveGame = saveGame.updateSession(updatedSession)
            renderCurrentRoom() // ri-mostra la safe aggiornata (e ri-salva)
        }
    }

    fun onEnterNextRoom() {
        val session = saveGame.saveSession ?: return
        viewModelScope.launch {
            val nextSession = session.updateRoomIndex(session.currentRoomIndex + 1)
            saveGame = saveGame.updateSession(nextSession)
            renderCurrentRoom()
        }
    }

    fun onOptions() {
        // TODO: navigazione schermata opzioni
    }

    private companion object {
        const val ACTION_DELAY_MS = 800L
    }
}