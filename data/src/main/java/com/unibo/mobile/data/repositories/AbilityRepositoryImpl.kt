package com.unibo.mobile.data.repositories

import com.unibo.mobile.domain.models.Ability
import com.unibo.mobile.domain.repositories.AbilityRepository

class AbilityRepositoryImpl : AbilityRepository {
    override suspend fun getAbilityByName(abilityName: String): Ability {
        TODO("Not yet implemented")
    }

    override suspend fun getAbilityFromList(abilityList: List<String>): List<Ability> {
        TODO("Not yet implemented")
    }

    override suspend fun getAbilityForPlayer(
        className: String,
        level: Int
    ): Ability {
        TODO("Not yet implemented")
    }

    override suspend fun getAbitityForEnemy(enemyName: String): Ability {
        TODO("Not yet implemented")
    }
}