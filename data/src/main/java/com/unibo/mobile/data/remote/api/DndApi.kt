package com.unibo.mobile.data.remote.api

import com.unibo.mobile.data.remote.models.SpellDto
import retrofit2.http.GET
import retrofit2.http.Path

interface DndApi {
    @GET(value = "spells/{index}")
    suspend fun getSpellByIndex(@Path("index") index: String): SpellDto

    /*
    @GET(value = "classes/{index}/spells")
    suspend fun getSpellsByClass(@Path("index") index: String): SpellListDto

    @GET(value = "monsters/{index}")
    suspend fun getMonster(@Path("index") index: String): MonsterDto

    @GET(value = "monsters")
    suspend fun getMonstersChallengeRating(@Query("challenge_rating") challengeRating: String): MonsterListDto

     */
}