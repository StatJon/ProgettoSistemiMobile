package com.unibo.mobile.data.remote.models.spell

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SpellListDto (
    @param:Json(name = "count") val count: Int,
    @param:Json(name = "results") val results: List<SpellListItemDto>
)