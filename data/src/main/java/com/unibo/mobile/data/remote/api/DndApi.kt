package com.unibo.mobile.data.remote.api

import com.unibo.mobile.data.remote.models.AreaOfEffectDto
import com.unibo.mobile.data.remote.models.SpellDto
import retrofit2.http.GET

interface DndApi {
    @GET(value="spells/fireball")
    suspend fun getFireball(): SpellDto
}