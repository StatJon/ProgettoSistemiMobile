package com.unibo.mobile.data.models.save

import com.unibo.mobile.domain.model.save.PlayerCharacter
import com.unibo.mobile.domain.model.save.SaveSession

data class SaveSessionImpl(
    override val currentRoomIndex: Int,
    override val playerCharacter: PlayerCharacter
) : SaveSession {
}