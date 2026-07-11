package com.unibo.mobile.domain.model.save

interface UserSettings {
    val musicVolume: Float
    val soundVolume: Float
    val localization: String

    fun manageVolume(): Float {
        TODO("Decidere in seguito come gestire livelli volume e muto")
    }
}