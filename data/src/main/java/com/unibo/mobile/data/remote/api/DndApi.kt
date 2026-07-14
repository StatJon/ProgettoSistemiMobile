package com.unibo.mobile.data.remote.api

import androidx.room.Index
import com.unibo.mobile.data.remote.models.monster.MonsterDto
import com.unibo.mobile.data.remote.models.monster.MonsterListDto
import com.unibo.mobile.data.remote.models.spell.SpellDto
import com.unibo.mobile.data.remote.models.spell.SpellListDto
import com.unibo.mobile.domain.model.dungeon.ChallengeRating
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface DndApi {
    @GET(value = "spells/{index}")
    suspend fun getSpell(@Path("index") index: String): SpellDto

    @GET(value = "monsters/{index}")
    suspend fun getMonster(@Path("index") index: String): MonsterDto

    @GET(value = "classes/{index}/spells")
    suspend fun getClassSpells(@Path("index") index: String): SpellListDto

    @GET(value = "monsters")
    suspend fun getMonstersChallengeRating(@Query("challenge_rating") challengeRating: String): MonsterListDto
}