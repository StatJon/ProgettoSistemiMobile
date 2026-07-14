package com.unibo.mobile.data.models.save

import com.unibo.mobile.domain.model.save.PlayerCharacter
import com.unibo.mobile.domain.model.save.SaveSession

data class SaveSessionImpl(
    override val currentRoomIndex: Int,
    override val playerCharacter: PlayerCharacter
) : SaveSession {
    override fun updateRoomIndex(newRoomIndex: Int) : SaveSession {
        return this.copy(currentRoomIndex = newRoomIndex)
    }

    override fun updatePlayerCharacter(updatedCharacter: PlayerCharacter) : SaveSession {
        return this.copy(playerCharacter = updatedCharacter)
    }
}