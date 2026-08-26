package com.unibo.mobile.data.remote.mappers

import com.unibo.mobile.data.remote.models.MonsterIndexDto

class MonsterDtoListToListMapper {
    fun invoke(monsterIndexDtoList: List<MonsterIndexDto>) : List<String> {
        return monsterIndexDtoList.map { it.index }
    }
}