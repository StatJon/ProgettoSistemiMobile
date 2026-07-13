package com.unibo.mobile.domain.model.save

interface UserSettings {
    val musicEnable: Boolean
    val musicVolume: Float
    val soundEnable : Boolean
    val soundVolume: Float
    val language: String

}