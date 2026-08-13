package com.unibo.mobile.data.di

import com.unibo.mobile.data.repositories.PlayerClassRepositoryImpl
import com.unibo.mobile.domain.di.RepositoryProvider
import com.unibo.mobile.domain.repositories.PlayerClassRepository

class RepositoryProviderImpl : RepositoryProvider{
    override val playerClassRepository: PlayerClassRepository = PlayerClassRepositoryImpl()
}