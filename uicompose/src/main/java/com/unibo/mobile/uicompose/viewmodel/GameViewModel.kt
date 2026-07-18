package com.unibo.mobile.uicompose.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unibo.mobile.domain.model.entity.PlayerClass
import com.unibo.mobile.domain.model.save.SaveGame
import com.unibo.mobile.domain.usecases.DungeonUseCase
import com.unibo.mobile.domain.usecases.RoomCombatUseCase
import com.unibo.mobile.domain.usecases.RoomSafeUseCase
import com.unibo.mobile.domain.usecases.SaveUseCase
import com.unibo.mobile.domain.usecases.StaticDataUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class GameViewModel(
    private val staticDataUseCase: StaticDataUseCase,
    private val saveUseCase: SaveUseCase,
    private val dungeonUseCase: DungeonUseCase,
    private val roomSafeUseCase: RoomSafeUseCase,
    private val roomCombatUseCase: RoomCombatUseCase
) : ViewModel() {
    private val _state = MutableStateFlow<GameState>(GameState.Loading)
    val state: StateFlow<GameState> = _state.asStateFlow()
    private lateinit var saveGame:
            SaveGame
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
        // TODO: avvio nuova partita con la classe selezionata
    }

    fun onContinue() {
        // TODO: caricamento partita salvata
    }

    fun onOptions() {
        // TODO: navigazione schermata opzioni
    }
}