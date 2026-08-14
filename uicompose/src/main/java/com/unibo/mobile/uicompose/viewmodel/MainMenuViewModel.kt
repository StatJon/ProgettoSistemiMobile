package com.unibo.mobile.uicompose.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unibo.mobile.domain.models.PlayerClass
import com.unibo.mobile.domain.usecases.GetAllPlayerClassesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/* Classe ViewModel di riferimento,
con commenti di spiegazione per la creazione degli alti viewmodel
*/

class MainMenuViewModel(
    /* ----- Costruttore, parametri -----*/
    //contiene gli usecase che verranno usati nella classe
    private val getAllPlayerClassesUseCase: GetAllPlayerClassesUseCase
) : ViewModel() {

    /* ----- Dichiarazione tramite StateFlow ----- */
    //(private MutableStateFlow + public StateFlow)
    private val _playerClassesList = MutableStateFlow<List<PlayerClass>>(emptyList())
    val playerClassesList: StateFlow<List<PlayerClass>> = _playerClassesList

    /* ----- Operazioni all'avvio ----- */
    init {
        fetchPlayerClasses()
    }

    /* ----- Funzioni interne ----- */
    private fun fetchPlayerClasses() {
        //Nota: viewModelScope sostituisce suspend perchè crea una coroutine
        viewModelScope.launch {
            _playerClassesList.value = getAllPlayerClassesUseCase.invoke()
        }
    }
}