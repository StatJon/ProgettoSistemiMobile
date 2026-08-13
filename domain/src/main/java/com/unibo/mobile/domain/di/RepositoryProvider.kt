package com.unibo.mobile.domain.di

import com.unibo.mobile.domain.repositories.PlayerClassRepository

interface RepositoryProvider {
    val playerClassRepository : PlayerClassRepository
}