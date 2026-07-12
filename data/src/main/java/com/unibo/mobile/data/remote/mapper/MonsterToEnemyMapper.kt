package com.unibo.mobile.data.remote.mapper

import com.unibo.mobile.data.remote.models.monster.ActionsDto
import com.unibo.mobile.data.remote.models.monster.MonsterDto
import com.unibo.mobile.domain.model.ability.Ability
import com.unibo.mobile.domain.model.entity.Enemy

// Main Function
fun MonsterDto.toEnemyOrNull(): Enemy? {
    return null
}

// Helper Functions
private fun extractActions(monsterDto: MonsterDto): List<Ability>?{
    val actionDtoList: List<ActionsDto> = monsterDto.actions.filter {!it.damage.isNullOrEmpty()}
    if (actionDtoList.isEmpty()) return null
    val pairList : List<Pair<String, String>> = actionDtoList.map { actionDto ->
        val name = actionDto.name
        val damage = actionDto.damage!!.first().damageDice
        Pair (name, damage)
    }
    return null
}


