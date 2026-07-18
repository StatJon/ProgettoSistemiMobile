package com.unibo.mobile.data.models.save

import com.unibo.mobile.domain.model.save.SaveGame
import com.unibo.mobile.domain.model.save.SaveSession
import com.unibo.mobile.domain.model.save.UserSettings

data class SaveGameImpl(
    override val saveSession: SaveSession?,
    override val userSettings: UserSettings,
    override val winCounter: Int
) : SaveGame {
    override fun updateSession(saveSession: SaveSession?): SaveGame {
        return this.copy(saveSession = saveSession)
    }

    override fun incrementWinCounter(): SaveGame {
        return this.copy(winCounter = winCounter + 1)
    }
}