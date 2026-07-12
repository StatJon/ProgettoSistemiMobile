package com.unibo.mobile.data.remote.models.monster

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MonsterDto(
    @param:Json(name = "index") val index: String,
    @param:Json(name = "name") val name: String,
)