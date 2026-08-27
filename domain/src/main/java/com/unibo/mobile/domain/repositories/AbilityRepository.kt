package com.unibo.mobile.domain.repositories

import com.unibo.mobile.domain.models.Ability

interface AbilityRepository {
    suspend fun getAbilityByName(abilityName: String) : Ability?
    suspend fun getAbilityFromIndexList(abilityList: List<String>) : List<Ability> //nota per impl, deve chiamare getAbilityByName in loop oppure usare un altra chiamata ad hoc verso api

    suspend fun getAbilityListIndexAndLevelFromClass(className: String) : List<Pair<String, Int>>?

}