package com.unibo.mobile.data.repositories

import com.unibo.mobile.data.remote.api.DndApi
import com.unibo.mobile.data.remote.api.SafeApiCaller
import com.unibo.mobile.data.remote.mappers.SpellToAbilityMapper
import com.unibo.mobile.domain.models.Ability
import com.unibo.mobile.domain.repositories.AbilityRepository

class AbilityRepositoryImpl(
    private val dndApi: DndApi,
    private val safeApiCaller: SafeApiCaller,
    private val spellToAbilityMapper: SpellToAbilityMapper
) : AbilityRepository {
    override suspend fun getAbilityByName(abilityName: String): Ability? {
        val dto = safeApiCaller.invoke { dndApi.getSpellByIndex(abilityName) } ?: return null
        val ability = spellToAbilityMapper.invoke(dto)
        return ability
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