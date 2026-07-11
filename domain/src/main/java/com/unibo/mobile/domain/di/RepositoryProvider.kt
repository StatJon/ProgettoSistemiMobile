package com.unibo.mobile.domain.di

import com.unibo.mobile.domain.repositories.ApiRepository
import com.unibo.mobile.domain.repositories.LocalRepository

interface RepositoryProvider {
    val localRepository: LocalRepository
    val apiRepository: ApiRepository
}