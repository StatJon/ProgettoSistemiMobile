package com.unibo.mobile.data.remote.models.monster

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MonsterListDto(
    @param:Json(name = "count") val count: Int,
    @param:Json(name = "results") val results: List<MonsterListEntryDto>
)
