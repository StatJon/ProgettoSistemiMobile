package com.unibo.mobile.uicompose.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unibo.mobile.domain.models.PlayerClass
import com.unibo.mobile.domain.models.SaveGame
import com.unibo.mobile.domain.usecases.gamedata.GetAllPlayerClassesUseCase
import com.unibo.mobile.domain.usecases.savegame.LoadSaveGameUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/* Classe ViewModel di riferimento,
 * con commenti di spiegazione per la creazione degli alti viewmodel
*/

class MainMenuViewModel(
    // --- Costruttore, parametri
    // inserire gli usecase che verranno usati nella classe
    private val getAllPlayerClassesUseCase: GetAllPlayerClassesUseCase,
    private val loadSaveGameUseCase: LoadSaveGameUseCase
) : ViewModel() {

    // --- Dichiarazione tramite StateFlow
    // (private MutableStateFlow + public StateFlow)

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _playerClassesList = MutableStateFlow<List<PlayerClass>>(emptyList())
    val playerClassesList: StateFlow<List<PlayerClass>> = _playerClassesList

    private val _saveGame = MutableStateFlow(SaveGame(0, null))
    val saveGame: StateFlow<SaveGame> = _saveGame

    // --- Operazioni all'avvio
    init {
        _isLoading.value = true
        viewModelScope.launch {
            fetchPlayerClasses()
            fetchSaveData()
        }
        _isLoading.value = false
    }

    // --- Funzioni interne
    private suspend fun fetchPlayerClasses() {
        _playerClassesList.value = getAllPlayerClassesUseCase.invoke()
    }

    private suspend fun fetchSaveData() {
        _saveGame.value = loadSaveGameUseCase.invoke()
    }
}