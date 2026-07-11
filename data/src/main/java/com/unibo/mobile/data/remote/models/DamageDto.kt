package com.unibo.mobile.data.remote.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DamageDto(
    val damage_dice: String
)
