package com.unibo.mobile.domain.di

import com.unibo.mobile.domain.repositories.PlayerClassRepository
import com.unibo.mobile.domain.repositories.SaveGameRepository

interface RepositoryProvider {
    val playerClassRepository : PlayerClassRepository
    val saveGameRepository : SaveGameRepository
}