package com.unibo.mobile.progettosistemimobile

import android.app.Application
import com.unibo.mobile.data.di.RepositoryProviderImpl

import com.unibo.mobile.domain.di.UseCaseProvider

class GameApp : Application() {
    override fun onCreate() {
        super.onCreate()

        UseCaseProvider.setup(
            repositoryProvider = RepositoryProviderImpl()
        )
    }
}