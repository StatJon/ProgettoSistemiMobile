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

}