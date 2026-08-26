package com.unibo.mobile.domain.di

import com.unibo.mobile.domain.usecases.api.FetchAbilityByNameUseCase
import com.unibo.mobile.domain.usecases.api.FetchAbilityByNameUseCaseImpl
import com.unibo.mobile.domain.usecases.api.FetchEnemyByChallengeRatingUseCase
import com.unibo.mobile.domain.usecases.api.FetchEnemyByChallengeRatingUseCaseImpl
import com.unibo.mobile.domain.usecases.gamedata.GetAllPlayerClassesUseCase
import com.unibo.mobile.domain.usecases.gamedata.GetAllPlayerClassesUseCaseImpl
import com.unibo.mobile.domain.usecases.gamedata.GetPlayerClassByClassNameUseCase
import com.unibo.mobile.domain.usecases.gamedata.GetPlayerClassByClassNameUseCaseImpl
import com.unibo.mobile.domain.usecases.gamelogic.ApplyAbilityResultUseCase
import com.unibo.mobile.domain.usecases.gamelogic.ApplyAbilityResultUseCaseImpl
import com.unibo.mobile.domain.usecases.gamelogic.ApplyPlayerAbilityCostUseCase
import com.unibo.mobile.domain.usecases.gamelogic.ApplyPlayerAbilityCostUseCaseImpl
import com.unibo.mobile.domain.usecases.gamelogic.CalculateAbilityResultUseCase
import com.unibo.mobile.domain.usecases.gamelogic.CalculateAbilityResultUseCaseImpl
import com.unibo.mobile.domain.usecases.gamelogic.CheckCombatStatusUseCase
import com.unibo.mobile.domain.usecases.gamelogic.CheckCombatStatusUseCaseImpl
import com.unibo.mobile.domain.usecases.gamelogic.DetermineChallengeRatingUseCase
import com.unibo.mobile.domain.usecases.gamelogic.DetermineChallengeRatingUseCaseImpl
import com.unibo.mobile.domain.usecases.gamelogic.DetermineGamePhaseUseCase
import com.unibo.mobile.domain.usecases.gamelogic.DetermineGamePhaseUseCaseImpl
import com.unibo.mobile.domain.usecases.savegame.LoadSaveGameUseCase
import com.unibo.mobile.domain.usecases.savegame.LoadSaveGameUseCaseImpl
import com.unibo.mobile.domain.usecases.savegame.NewSaveSessionUseCase
import com.unibo.mobile.domain.usecases.savegame.NewSaveSessionUseCaseImpl
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
    lateinit var newSaveSessionUseCase: NewSaveSessionUseCase
    lateinit var fetchAbilityByNameUseCase: FetchAbilityByNameUseCase
    lateinit var calculateAbilityResultUseCase: CalculateAbilityResultUseCase
    lateinit var determineGamePhaseUseCase: DetermineGamePhaseUseCase
    lateinit var determineChallengeRatingUseCase: DetermineChallengeRatingUseCase
    lateinit var fetchEnemyByChallengeRatingUseCase: FetchEnemyByChallengeRatingUseCase
    lateinit var applyAbilityResultUseCase: ApplyAbilityResultUseCase
    lateinit var checkCombatStatusUseCase: CheckCombatStatusUseCase
    lateinit var applyPlayerAbilityCostUseCase: ApplyPlayerAbilityCostUseCase

    /**
     * Constructor
     * Creates associations between the UseCasesImpl and the proper repository
     * @param repositoryProvider the repository provider which defines the repositories to be used
     */

    fun setup(
        repositoryProvider: RepositoryProvider
    ) {
        getAllPlayerClassesUseCase = GetAllPlayerClassesUseCaseImpl(
            gamedataRepository = repositoryProvider.gamedataRepository
        )
        getPlayerClassByClassNameUseCase = GetPlayerClassByClassNameUseCaseImpl(
            gamedataRepository = repositoryProvider.gamedataRepository
        )
        loadSaveGameUseCase = LoadSaveGameUseCaseImpl(
            saveGameRepository = repositoryProvider.saveGameRepository
        )
        saveSaveGameUseCase = SaveSaveGameUseCaseImpl(
            saveGameRepository = repositoryProvider.saveGameRepository
        )
        newSaveSessionUseCase = NewSaveSessionUseCaseImpl(
            saveGameRepository = repositoryProvider.saveGameRepository
        )
        fetchAbilityByNameUseCase = FetchAbilityByNameUseCaseImpl(
            abilityRepository = repositoryProvider.abilityRepository
        )

        fetchEnemyByChallengeRatingUseCase = FetchEnemyByChallengeRatingUseCaseImpl(
            enemyRepository = repositoryProvider.enemyRepository
        )

        calculateAbilityResultUseCase = CalculateAbilityResultUseCaseImpl()

        determineGamePhaseUseCase = DetermineGamePhaseUseCaseImpl()

        determineChallengeRatingUseCase = DetermineChallengeRatingUseCaseImpl()

        applyAbilityResultUseCase = ApplyAbilityResultUseCaseImpl()

        checkCombatStatusUseCase = CheckCombatStatusUseCaseImpl()

        applyPlayerAbilityCostUseCase = ApplyPlayerAbilityCostUseCaseImpl()

    }
}