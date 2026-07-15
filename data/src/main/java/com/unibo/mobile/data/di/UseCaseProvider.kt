package com.unibo.mobile.data.di

import com.unibo.mobile.data.models.strategy.CommonActionStrategyDefault
import com.unibo.mobile.data.models.strategy.DiceThrowStrategyDefault
import com.unibo.mobile.data.models.strategy.LevelUpStrategyDefault
import com.unibo.mobile.data.models.strategy.NpcStrategyDefault
import com.unibo.mobile.data.models.strategy.ResourceCostDefault
import com.unibo.mobile.data.models.strategy.TurnCalculatorSingleAllyDefault
import com.unibo.mobile.data.usecases.DungeonUseCaseImpl
import com.unibo.mobile.data.usecases.RoomCombatUseCaseImpl
import com.unibo.mobile.data.usecases.RoomSafeUseCaseImpl
import com.unibo.mobile.data.usecases.SaveUseCaseImpl
import com.unibo.mobile.domain.di.RepositoryProvider
import com.unibo.mobile.domain.usecases.DungeonUseCase
import com.unibo.mobile.domain.usecases.RoomCombatUseCase
import com.unibo.mobile.domain.usecases.RoomSafeUseCase
import com.unibo.mobile.domain.usecases.SaveUseCase

object UseCaseProvider {
    lateinit var roomCombatUseCase: RoomCombatUseCase
    lateinit var roomSafeUseCase: RoomSafeUseCase
    lateinit var dungeonUseCase: DungeonUseCase
    lateinit var saveUseCase: SaveUseCase

    fun setup(repositoryProvider: RepositoryProvider) {
        val turnCalculator = TurnCalculatorSingleAllyDefault()
        val npcStrategy = NpcStrategyDefault()
        val diceThrowStrategy = DiceThrowStrategyDefault()
        val commonActionStrategy = CommonActionStrategyDefault()
        val resourceCost = ResourceCostDefault()
        val levelUpStrategy = LevelUpStrategyDefault()

        roomCombatUseCase = RoomCombatUseCaseImpl(
            turnCalculator = turnCalculator,
            npcStrategy = npcStrategy,
            diceThrowStrategy = diceThrowStrategy,
            commonActionStrategy = commonActionStrategy,
            resourceCost = resourceCost
        )

        roomSafeUseCase = RoomSafeUseCaseImpl(
            apiRepository = repositoryProvider.apiRepository,
            levelUpStrategy = levelUpStrategy
        )

        dungeonUseCase = DungeonUseCaseImpl()

        saveUseCase = SaveUseCaseImpl(
            localRepository = repositoryProvider.localRepository
        )
    }
}
