package com.unibo.mobile.domain.models

sealed interface Ability {
    val name: String
    val level: Int
    val isAoe: Boolean
}