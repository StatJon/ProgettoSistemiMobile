package com.unibo.mobile.domain.model.save

interface SaveGame {
    val saveSession: SaveSession?
    val userSettings: UserSettings
    val winCounter: Int
    fun updateSession(saveSession: SaveSession?): SaveGame
    fun incrementWinCounter(): SaveGame
}