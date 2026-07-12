package com.unibo.mobile.data.remote.models.monster

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MonsterDto(
    @param:Json(name = "index") val index: String,
    @param:Json(name = "name") val name: String,
    @param:Json(name = "type") val type: String,
    @param:Json(name = "armor_class") val armorClassDto: List<ArmorClassDto>,
    @param:Json(name = "hit_points") val hitPoints: Int,
    @param:Json(name = "dexterity") val dexterity: Int,
    @param:Json(name = "challenge_rating") val challengeRating: Float,
    @param:Json(name = "xp") val xp: Int,
    @param:Json(name = "actions") val actions: List<ActionsDto>
)