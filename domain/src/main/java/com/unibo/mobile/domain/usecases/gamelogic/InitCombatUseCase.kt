package com.unibo.mobile.domain.usecases.gamelogic

import com.unibo.mobile.domain.models.CombatSnapshot

interface InitCombatUseCase {
    fun invoke(): CombatSnapshot
}

class InitCombatUseCaseImpl: InitCombatUseCase{
    override fun invoke(): CombatSnapshot {
        TODO("Not yet implemented")
    }
}

