package com.unibo.mobile.data.remote.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SpellDamageDto(
    @param:Json(name = "damage_at_slot_level") val damageAtSlotLevel: Map<String, String>
)
