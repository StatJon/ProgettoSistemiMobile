package com.unibo.mobile.data.remote.models.spell

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class AreaOfEffectDto(
    @param:Json(name = "size") val size: Int
)
