package com.unibo.mobile.data.remote.mappers

import com.unibo.mobile.data.remote.models.ClassSpellDtoList

class ClassSpellDtoListToPairMapper {
    fun invoke(classSpellDtoList: ClassSpellDtoList): List<Pair<String, Int>> {
        return classSpellDtoList.results.map { Pair(it.index, it.level) }
    }
}