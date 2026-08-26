package com.unibo.mobile.data.remote.api


import com.unibo.mobile.data.remote.models.MonsterDto
import com.unibo.mobile.data.remote.models.MonsterListResponseDto
import com.unibo.mobile.data.remote.models.SpellDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface DndApi {
    @GET(value = "spells/{index}")
    suspend fun getSpellByIndex(@Path("index") index: String): SpellDto

    @GET("monsters")
    suspend fun getMonsterListByChallengeRating(
        @Query("challenge_rating") challengeRating: Float
    ): MonsterListResponseDto

    @GET(value = "monsters/{index}")
    suspend fun getMonsterByIndex(@Path("index") index: String): MonsterDto

    /*
    @GET(value = "classes/{index}/spells")
    suspend fun getSpellsByClass(@Path("index") index: String): SpellListDto

    @GET(value = "monsters/{index}")
    suspend fun getMonster(@Path("index") index: String): MonsterDto

    @GET(value = "monsters")
    suspend fun getMonstersChallengeRating(@Query("challenge_rating") challengeRating: String): MonsterListDto

     */
}