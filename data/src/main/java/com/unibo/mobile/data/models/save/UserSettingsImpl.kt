package com.unibo.mobile.data.models.save

import com.unibo.mobile.domain.model.save.UserSettings

data class UserSettingsImpl(
    override val musicVolume: Float,
    override val soundVolume: Float,
    override val localization: String
) : UserSettings {
}