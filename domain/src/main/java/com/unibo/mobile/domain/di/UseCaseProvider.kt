package com.unibo.mobile.domain.di

import com.unibo.mobile.domain.usecases.api.FetchAbilityByNameUseCase
import com.unibo.mobile.domain.usecases.api.FetchAbilityByNameUseCaseImpl
import com.unibo.mobile.domain.usecases.gamedata.GetAllPlayerClassesUseCase
import com.unibo.mobile.domain.usecases.gamedata.GetAllPlayerClassesUseCaseImpl
import com.unibo.mobile.domain.usecases.gamedata.GetPlayerClassByClassNameUseCase
import com.unibo.mobile.domain.usecases.gamedata.GetPlayerClassByClassNameUseCaseImpl
import com.unibo.mobile.domain.usecases.gamelogic.CalculateAbilityResultUseCase
import com.unibo.mobile.domain.usecases.gamelogic.CalculateAbilityResultUseCaseImpl
import com.unibo.mobile.domain.usecases.savegame.LoadSaveGameUseCase
import com.unibo.mobile.domain.usecases.savegame.LoadSaveGameUseCaseImpl
import com.unibo.mobile.domain.usecases.savegame.SaveSaveGameUseCase
import com.unibo.mobile.domain.usecases.savegame.SaveSaveGameUseCaseImpl

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
    lateinit var fetchAbilityByNameUseCase: FetchAbilityByNameUseCase
    lateinit var calculateAbilityResultUseCase: CalculateAbilityResultUseCase

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
        fetchAbilityByNameUseCase = FetchAbilityByNameUseCaseImpl(
            abilityRepository = repositoryProvider.abilityRepository
        )
        calculateAbilityResultUseCase = CalculateAbilityResultUseCaseImpl()

    }
}