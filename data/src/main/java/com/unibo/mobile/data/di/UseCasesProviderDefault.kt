package com.unibo.mobile.data.di

import com.unibo.mobile.data.models.strategy.CommonActionStrategyDefault
import com.unibo.mobile.data.models.strategy.DiceThrowStrategyDefault
import com.unibo.mobile.data.models.strategy.DungeonCreationStrategyDefault
import com.unibo.mobile.data.models.strategy.LevelUpStrategyDefault
import com.unibo.mobile.data.models.strategy.NpcStrategyDefault
import com.unibo.mobile.data.models.strategy.ResourceCostDefault
import com.unibo.mobile.data.models.strategy.TurnCalculatorSingleAllyDefault
import com.unibo.mobile.data.usecases.DungeonUseCaseImpl
import com.unibo.mobile.data.usecases.RoomCombatUseCaseImpl
import com.unibo.mobile.data.usecases.RoomSafeUseCaseImpl
import com.unibo.mobile.data.usecases.SaveUseCaseImpl
import com.unibo.mobile.data.usecases.StaticDataUseCaseImpl
import com.unibo.mobile.domain.di.RepositoryProvider
import com.unibo.mobile.domain.di.UseCasesProvider
import com.unibo.mobile.domain.usecases.DungeonUseCase
import com.unibo.mobile.domain.usecases.RoomCombatUseCase
import com.unibo.mobile.domain.usecases.RoomSafeUseCase
import com.unibo.mobile.domain.usecases.SaveUseCase
import com.unibo.mobile.domain.usecases.StaticDataUseCase

class UseCasesProviderDefault(
    repositoryProvider: RepositoryProvider
) : UseCasesProvider {
    private val turnCalculator = TurnCalculatorSingleAllyDefault()
    private val npcStrategy = NpcStrategyDefault()
    private val diceThrowStrategy = DiceThrowStrategyDefault()
    private val commonActionStrategy = CommonActionStrategyDefault()
    private val resourceCost = ResourceCostDefault()
    private val levelUpStrategy = LevelUpStrategyDefault()
    private val dungeonCreationStrategy = DungeonCreationStrategyDefault()

    override val roomCombatUseCase: RoomCombatUseCase = RoomCombatUseCaseImpl(
        turnCalculator = turnCalculator,
        npcStrategy = npcStrategy,
        diceThrowStrategy = diceThrowStrategy,
        commonActionStrategy = commonActionStrategy,
        resourceCost = resourceCost,
        apiRepository = repositoryProvider.apiRepository
    )

    override val roomSafeUseCase: RoomSafeUseCase = RoomSafeUseCaseImpl(
        apiRepository = repositoryProvider.apiRepository,
        levelUpStrategy = levelUpStrategy
    )

    override val dungeonUseCase: DungeonUseCase = DungeonUseCaseImpl(
        dungeonCreationStrategy = dungeonCreationStrategy
    )

    override val saveUseCase: SaveUseCase = SaveUseCaseImpl(
        localRepository = repositoryProvider.localRepository
    )
    override val staticDataUseCase: StaticDataUseCase = StaticDataUseCaseImpl(
        localRepository = repositoryProvider.localRepository
    )
}

