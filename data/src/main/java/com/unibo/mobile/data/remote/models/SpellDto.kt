package com.unibo.mobile.data.remote.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class SpellDto(
    @param:Json(name = "index") val index: String,
    @param:Json(name = "name") val name: String,
    @param:Json(name = "range") val range: String,
    @param:Json(name = "casting_time") val castingTime: String,
    @param:Json(name = "level") val level: Int,
    @param:Json(name = "damage") val damage: SpellDamageDto?,
    @param:Json(name = "heal_at_slot_level") val healAtSlotLevel: Map<String, String>?,
    @param:Json(name = "area_of_effect") val areaOfEffect: AreaOfEffectDto?
)