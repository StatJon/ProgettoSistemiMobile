package com.unibo.mobile.progettosistemimobile

import android.app.Application
import com.unibo.mobile.data.di.RepositoryProviderImpl
import com.unibo.mobile.data.di.UseCasesProviderDefault
import com.unibo.mobile.domain.di.RepositoryProvider
import com.unibo.mobile.domain.di.UseCasesProvider

class DungeonGame : Application() {
    lateinit var useCasesProvider: UseCasesProvider

    override fun onCreate() {
        super.onCreate()
        val repositoryProvider: RepositoryProvider = RepositoryProviderImpl(context = this)
        useCasesProvider = UseCasesProviderDefault(repositoryProvider)
        android.util.Log.d("DungeonGame", ">>>Setup OK!<<<")
    }
}