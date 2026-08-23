package com.unibo.mobile.domain.usecases.gamedata

//TODO Verificare bene se servono parametri
interface SetupDungeonUseCase {
    suspend fun invoke(): Int
}