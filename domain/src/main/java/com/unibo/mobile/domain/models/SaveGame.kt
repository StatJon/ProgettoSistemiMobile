package com.unibo.mobile.domain.models

data class SaveGame(
    val winCounter: Int,
    val saveSession: SaveSession
) {
}