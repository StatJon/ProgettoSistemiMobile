package com.unibo.mobile.uicompose.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unibo.mobile.domain.models.PlayerClass
import com.unibo.mobile.domain.models.SaveGame
import com.unibo.mobile.domain.usecases.gamedata.GetAllPlayerClassesUseCase
import com.unibo.mobile.domain.usecases.savegame.LoadSaveGameUseCase
import com.unibo.mobile.domain.usecases.savegame.NewSaveSessionUseCase
import com.unibo.mobile.domain.usecases.savegame.SaveSaveGameUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainMenuViewModel(
    private val getAllPlayerClassesUseCase: GetAllPlayerClassesUseCase,
    private val loadSaveGameUseCase: LoadSaveGameUseCase,
    private val newSaveSessionUseCase: NewSaveSessionUseCase,
    private val saveSaveGameUseCase: SaveSaveGameUseCase
) : ViewModel() {

    // --- StateFlow

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _playerClassesList = MutableStateFlow<List<PlayerClass>>(emptyList())
    val playerClassesList: StateFlow<List<PlayerClass>> = _playerClassesList

    private val _saveGame = MutableStateFlow(SaveGame(0, null))
    val saveGame: StateFlow<SaveGame> = _saveGame

    private val _navigateToGame = MutableStateFlow(false)
    val navigateToGame: StateFlow<Boolean> = _navigateToGame

    // --- Init
    init {
        _isLoading.value = true
        viewModelScope.launch {
            fetchPlayerClasses()
            fetchSaveData()
            _isLoading.value = false
        }
    }

    // --- Public Functions
    fun onNewGameSelected(playerClass: PlayerClass) {
        viewModelScope.launch {
            _isLoading.value = true
            createNewSaveSession(playerClass)
            _isLoading.value = false
            _navigateToGame.value = true
        }
    }

    fun onContinueSelected() {
        viewModelScope.launch {
            if (_saveGame.value.saveSession != null) {
                _navigateToGame.value = true
            }
        }
    }

    fun resetNavigation() {
        _navigateToGame.value = false
    }

    fun refreshSaveData() {
        viewModelScope.launch {
            fetchSaveData()
        }
    }

    // --- Private Functions
    private suspend fun fetchPlayerClasses() {
        _playerClassesList.value = getAllPlayerClassesUseCase.invoke()
    }

    private suspend fun fetchSaveData() {
        _saveGame.value = loadSaveGameUseCase.invoke()
    }

    private suspend fun createNewSaveSession(playerClass: PlayerClass) {
        _saveGame.value = newSaveSessionUseCase.invoke(_saveGame.value, playerClass)
        saveSaveGameUseCase.invoke(_saveGame.value)
    }
}