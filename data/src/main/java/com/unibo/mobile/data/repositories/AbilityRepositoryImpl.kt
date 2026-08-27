package com.unibo.mobile.data.repositories

import com.unibo.mobile.data.remote.api.DndApi
import com.unibo.mobile.data.remote.api.SafeApiCaller
import com.unibo.mobile.data.remote.mappers.ClassSpellDtoListToPairMapper
import com.unibo.mobile.data.remote.mappers.SpellToAbilityMapper
import com.unibo.mobile.data.remote.models.ClassSpellDtoList
import com.unibo.mobile.domain.models.Ability
import com.unibo.mobile.domain.repositories.AbilityRepository

class AbilityRepositoryImpl(
    private val dndApi: DndApi,
    private val safeApiCaller: SafeApiCaller,
    private val spellToAbilityMapper: SpellToAbilityMapper,
    private val classSpellDtoListToPairMapper: ClassSpellDtoListToPairMapper,
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

    override suspend fun getAbilityListIndexAndLevelFromClass(className: String): List<Pair<String, Int>>? {
        val dtoAbilityListPair: ClassSpellDtoList =
            safeApiCaller.invoke { dndApi.getSpellListByClassName(className) } ?: return null
        val abilityIndexAndStringPairList = classSpellDtoListToPairMapper.invoke(dtoAbilityListPair)
        return abilityIndexAndStringPairList
    }
}
