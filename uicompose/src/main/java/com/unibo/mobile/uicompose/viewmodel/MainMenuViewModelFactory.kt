package com.unibo.mobile.uicompose.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.unibo.mobile.domain.usecases.GetAllPlayerClassesUseCase

class MainMenuViewModelFactory(
    /* ----- Costruttore, parametri -----*/
    //contiene gli usecase per le dipendenze usate nella classe
    private val getAllPlayerClassesUseCase: GetAllPlayerClassesUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainMenuViewModel::class.java)) {
            return MainMenuViewModel(getAllPlayerClassesUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}