package com.unibo.mobile.progettosistemimobile

import android.app.Application
import com.unibo.mobile.data.di.RepositoryProviderImpl
import com.unibo.mobile.data.di.UseCasesProvider
import com.unibo.mobile.domain.di.RepositoryProvider

class DungeonGame : Application() {
    override fun onCreate() {
        super.onCreate()
        val repositoryProvider: RepositoryProvider = RepositoryProviderImpl(context = this)
        UseCasesProvider.setup(repositoryProvider)
        android.util.Log.d("DungeonGame", ">>>Setup OK!<<<")
    }
}