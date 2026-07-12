package com.unibo.mobile.data.remote.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ArmorClassDto(
    @param:Json(name = "value") val value: Int
)
