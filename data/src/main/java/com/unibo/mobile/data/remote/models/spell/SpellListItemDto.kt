package com.unibo.mobile.data.remote.models.spell

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SpellListItemDto(
    @param:Json(name = "index") val index: String,
    @param:Json(name = "name") val name: String
)
