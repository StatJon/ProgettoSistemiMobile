package com.unibo.mobile.data

import com.unibo.mobile.data.remote.api.RetrofitClient
import com.unibo.mobile.data.remote.mapper.toAbilityOrNull
import com.unibo.mobile.data.remote.models.SpellDto
import kotlinx.coroutines.runBlocking
import org.junit.Test
import retrofit2.Retrofit


class RetrofitTest {

    @Test
    fun testGetFireball() = runBlocking {
        val client = RetrofitClient()
        val result = client.dndService.getFireballTestOnly()
        println(result)
    }

    @Test
    fun testMapFireballToAbility(): Unit = runBlocking {
        val client = RetrofitClient()
        val spellDto: SpellDto = client.dndService.getFireballTestOnly()
        val ability = spellDto.toAbilityOrNull()
        println(ability)
    }

    @Test
    fun testMassHealingWordToAbility(): Unit = runBlocking {
        val client = RetrofitClient()
        val spellDto: SpellDto = client.dndService.getMassHealingWordTestOnly()
        val ability = spellDto.toAbilityOrNull()
        println(ability)
    }

    @Test
    fun testMapBlessToAbility(): Unit = runBlocking {
        val client = RetrofitClient()
        val spellDto: SpellDto = client.dndService.getBlessTestOnly()
        val ability = spellDto.toAbilityOrNull()
        println(ability)
    } // Must return NULL

}