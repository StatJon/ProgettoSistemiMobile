package com.unibo.mobile.data

import com.unibo.mobile.data.remote.api.RetrofitClient
import com.unibo.mobile.data.remote.mapper.toAbilityOrNull
import com.unibo.mobile.data.remote.models.spell.SpellDto
import kotlinx.coroutines.runBlocking
import org.junit.Test

class RetrofitTest {

    @Test
    fun testSimpleGetFireball() = runBlocking {
        val client = RetrofitClient()
        val result = client.dndService.getSpell("fireball")
        println(result)
    }

    @Test
    fun testMapDamageSpellToAbility(): Unit = runBlocking {
        val client = RetrofitClient()
        val spellDto: SpellDto = client.dndService.getSpell("fireball")
        val ability = spellDto.toAbilityOrNull()
        println(ability)
    }

    @Test
    fun testMapHealingSpellToAbility(): Unit = runBlocking {
        val client = RetrofitClient()
        val spellDto: SpellDto = client.dndService.getSpell("mass-healing-word")
        val ability = spellDto.toAbilityOrNull()
        println(ability)
    }

    @Test
    fun testMapNonCompatibleNull(): Unit = runBlocking {
        val client = RetrofitClient()
        val spellDto: SpellDto = client.dndService.getSpell("bless")
        val ability = spellDto.toAbilityOrNull()
        println(ability)
    } // Must return NULL

}