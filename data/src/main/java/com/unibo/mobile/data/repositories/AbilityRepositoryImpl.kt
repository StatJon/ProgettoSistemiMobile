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
        if (abilityName.isBlank()) return null
        println("DEBUG: Fetching ability with name: $abilityName")
        val dto = safeApiCaller.invoke { dndApi.getSpellByIndex(abilityName) } ?: run {
            println("DEBUG: API returned null for $abilityName")
            return null
        }
        println("DEBUG: DTO received: $dto")
        val ability = spellToAbilityMapper.invoke(dto)
        return ability
    }


    override suspend fun getAbilityFromIndexList(abilityList: List<String>): List<Ability> {
        return abilityList.mapNotNull { abilityName ->
            getAbilityByName(abilityName)
        }
    }
    /*
        override suspend fun getAbilityForPlayer(
            className: String,
            level: Int
        ): Ability {
            TODO("Not yet implemented")
        }

        override suspend fun getAbitityForEnemy(enemyName: String): Ability {
            TODO("Not yet implemented")
         */
}
