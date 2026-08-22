package com.unibo.mobile.domain.di

import com.unibo.mobile.domain.repositories.SaveGameRepository
import com.unibo.mobile.domain.usecases.GetAllPlayerClassesUseCase
import com.unibo.mobile.domain.usecases.GetAllPlayerClassesUseCaseImpl
import com.unibo.mobile.domain.usecases.GetPlayerClassByClassNameUseCase
import com.unibo.mobile.domain.usecases.GetPlayerClassByClassNameUseCaseImpl
import com.unibo.mobile.domain.usecases.LoadSaveGameUseCase
import com.unibo.mobile.domain.usecases.LoadSaveGameUseCaseImpl
import com.unibo.mobile.domain.usecases.SaveSaveGameUseCase
import com.unibo.mobile.domain.usecases.SaveSaveGameUseCaseImpl

/**
 * Wiring Object Class
 * Lists and wires all usecases with their respective repositories,
 * constructs the actual usecases to be used by the app.
 */
object UseCaseProvider {
    lateinit var getAllPlayerClassesUseCase: GetAllPlayerClassesUseCase
    lateinit var getPlayerClassByClassNameUseCase: GetPlayerClassByClassNameUseCase
    lateinit var loadSaveGameUseCase: LoadSaveGameUseCase
    lateinit var saveSaveGameUseCase: SaveSaveGameUseCase

    /**
     * Constructor
     * Creates associations between the UseCasesImpl and the proper repository
     * @param repositoryProvider the repository provider which defines the repositories to be used
     */

    fun setup(
        repositoryProvider: RepositoryProvider
    ) {
        getAllPlayerClassesUseCase = GetAllPlayerClassesUseCaseImpl(
            playerClassRepository = repositoryProvider.playerClassRepository
        )
        getPlayerClassByClassNameUseCase = GetPlayerClassByClassNameUseCaseImpl(
            playerClassRepository = repositoryProvider.playerClassRepository
        )
        loadSaveGameUseCase = LoadSaveGameUseCaseImpl(
            saveGameRepository = repositoryProvider.saveGameRepository
        )
        saveSaveGameUseCase = SaveSaveGameUseCaseImpl(
            saveGameRepository = repositoryProvider.saveGameRepository
        )

    }
}