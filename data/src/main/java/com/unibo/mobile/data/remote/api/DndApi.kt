package com.unibo.mobile.data.remote.api

import com.unibo.mobile.data.remote.models.ClassSpellDtoList
import com.unibo.mobile.data.remote.models.MonsterDto
import com.unibo.mobile.data.remote.models.MonsterListResponseDto
import com.unibo.mobile.data.remote.models.SpellDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface DndApi {
    @GET(value = "spells/{index}")
    suspend fun getSpellByIndex(
        @Path("index") index: String
    ): SpellDto

    @GET("classes/{index}/spells") //https://www.dnd5eapi.co/api/2014/classes/cleric/spells
    suspend fun getSpellListByClassName(
        @Path("index") index: String
    ): ClassSpellDtoList

    @GET("monsters")
    suspend fun getMonsterListByChallengeRating(
        @Query("challenge_rating") challengeRating: Float
    ): MonsterListResponseDto

    @GET(value = "monsters/{index}")
    suspend fun getMonsterByIndex(
        @Path("index") index: String
    ): MonsterDto
}