package com.unibo.mobile.data.remote.api

import com.unibo.mobile.data.remote.models.monster.MonsterDto
import com.unibo.mobile.data.remote.models.spell.SpellDto
import retrofit2.http.GET
import retrofit2.http.Path

interface DndApi {
    @GET(value = "spells/{index}")
    suspend fun getSpell(@Path("index") index: String): SpellDto

    @GET(value = "monsters/{index}")
    suspend fun getMonster(@Path("index") index: String): MonsterDto

}