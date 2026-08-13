package com.unibo.mobile.domain.di

import com.unibo.mobile.domain.usecases.GetAllPlayerClassesUseCase
import com.unibo.mobile.domain.usecases.GetAllPlayerClassesUseCaseImpl
import com.unibo.mobile.domain.usecases.GetPlayerClassByClassNameUseCase
import com.unibo.mobile.domain.usecases.GetPlayerClassByClassNameUseCaseImpl

/**
 * Wiring Object Class
 * Lists and wires all usecases with their respective repositories,
 * constructs the actual usecases to be used by the app.
 */
object UseCaseProvider {
    lateinit var getAllPlayerClassesUseCase: GetAllPlayerClassesUseCase
    lateinit var getPlayerClassByClassNameUseCase: GetPlayerClassByClassNameUseCase

    /**
     * Constructor
     * Creates associations between the UseCasesImpl and the proper repository
     * @param repositoryProvider the repository provider which defines the repositories to be used
     */

    fun setup(repositoryProvider: RepositoryProvider) {
        getAllPlayerClassesUseCase = GetAllPlayerClassesUseCaseImpl(
            playerClassRepository = repositoryProvider.playerClassRepository
        )
        getPlayerClassByClassNameUseCase = GetPlayerClassByClassNameUseCaseImpl(
            playerClassRepository = repositoryProvider.playerClassRepository
        )
    }
}