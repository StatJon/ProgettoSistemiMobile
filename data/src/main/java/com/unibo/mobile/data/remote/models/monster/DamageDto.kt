package com.unibo.mobile.data.remote.models.monster

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DamageDto(
    @param:Json(name = "damage_dice") val damageDice: String
)
