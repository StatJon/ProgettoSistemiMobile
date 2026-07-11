package com.unibo.mobile.domain.model.save

interface SaveGame {
    val saveSession: SaveSession?
    val userSettings: UserSettings
    val winCounter: Int
}