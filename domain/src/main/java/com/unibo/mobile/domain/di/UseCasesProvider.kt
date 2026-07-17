package com.unibo.mobile.domain.di

import com.unibo.mobile.domain.usecases.RoomCombatUseCase
import com.unibo.mobile.domain.usecases.RoomSafeUseCase
import com.unibo.mobile.domain.usecases.DungeonUseCase
import com.unibo.mobile.domain.usecases.SaveUseCase
import com.unibo.mobile.domain.usecases.StaticDataUseCase

interface UseCasesProvider {
    val roomCombatUseCase: RoomCombatUseCase
    val roomSafeUseCase: RoomSafeUseCase
    val dungeonUseCase: DungeonUseCase
    val saveUseCase: SaveUseCase
    val staticDataUseCase: StaticDataUseCase
}