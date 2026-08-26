package com.unibo.mobile.uicompose.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.unibo.mobile.domain.usecases.gamedata.GetAllPlayerClassesUseCase
import com.unibo.mobile.domain.usecases.savegame.LoadSaveGameUseCase
import com.unibo.mobile.domain.usecases.savegame.NewSaveSessionUseCase
import com.unibo.mobile.domain.usecases.savegame.SaveSaveGameUseCase

class MainMenuViewModelFactory(
    /* ----- Costruttore, parametri -----*/
    //contiene gli usecase per le dipendenze usate nella classe
    private val getAllPlayerClassesUseCase: GetAllPlayerClassesUseCase,
    private val loadSaveGameUseCase: LoadSaveGameUseCase,
    private val newSaveSessionUseCase: NewSaveSessionUseCase,
    private val saveSaveGameUseCase: SaveSaveGameUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainMenuViewModel::class.java)) {
            return MainMenuViewModel(
                getAllPlayerClassesUseCase,
                loadSaveGameUseCase,
                newSaveSessionUseCase,
                saveSaveGameUseCase
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}