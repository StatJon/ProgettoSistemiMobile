package com.unibo.mobile.data.remote.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MonsterListResponseDto(
    val count: Int,
    val results: List<MonsterIndexDto>
)

@JsonClass(generateAdapter = true)
data class MonsterIndexDto(
    @param:Json(name = "index") val index: String,
)

@JsonClass(generateAdapter = true)
data class MonsterDto(
    @param:Json(name = "index") val index: String,
    @param:Json(name = "name") val name: String,
    @param:Json(name = "type") val type: String,
    @param:Json(name = "hit_points") val hitPoints: Int,
    @param:Json(name = "armor_class") val armorClass: List<ArmorClassDto>?,
    @param:Json(name = "actions") val actions: List<ActionDto>?,
    @param:Json(name = "special_abilities") val specialAbilities: List<SpecialAbilityDto>?
)

@JsonClass(generateAdapter = true)
data class ArmorClassDto(
    @param:Json(name = "value") val value: Int
)

@JsonClass(generateAdapter = true)
data class ActionDto(
    @param:Json(name = "name") val name: String,
    @param:Json(name = "desc") val description: String,
    @param:Json(name = "damage") val damage: List<ActionDamageDto>?
)

@JsonClass(generateAdapter = true)
data class ActionDamageDto(
    @param:Json(name = "damage_dice") val damageDice: String? = null
)

@JsonClass(generateAdapter = true)
data class SpecialAbilityDto(
    @param:Json(name = "name") val name: String,
    @param:Json(name = "desc") val description: String,
    @param:Json(name = "spellcasting") val spellcasting: SpellcastingDto?
)

@JsonClass(generateAdapter = true)
data class SpellcastingDto(
    @param:Json(name = "spells") val spells: List<MonsterSpellIndexDto>?
)

@JsonClass(generateAdapter = true)
data class MonsterSpellIndexDto(
    @param:Json(name = "index") val index: String?
)