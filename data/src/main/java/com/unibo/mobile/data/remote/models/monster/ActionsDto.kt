package com.unibo.mobile.data.remote.models.monster

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ActionsDto(
    @param:Json(name = "name") val name: String,
    @param:Json(name = "damage") val damage: List<DamageDto>?
)
