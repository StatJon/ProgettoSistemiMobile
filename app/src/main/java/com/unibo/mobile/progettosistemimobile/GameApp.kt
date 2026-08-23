package com.unibo.mobile.progettosistemimobile

import android.app.Application
import androidx.room.Room
import com.unibo.mobile.data.di.RepositoryProviderImpl
import com.unibo.mobile.data.local.db.AppDatabase
import com.unibo.mobile.data.remote.api.RetrofitClient
import com.unibo.mobile.data.repositories.PlayerClassRepositoryImpl

import com.unibo.mobile.domain.di.UseCaseProvider

class GameApp : Application() {
    override fun onCreate() {
        super.onCreate()

        val database = Room.databaseBuilder(
            this,
            AppDatabase::class.java,
            "game_db"
        ).build()
        val dndApi = RetrofitClient().dndService

        UseCaseProvider.setup(
            repositoryProvider = RepositoryProviderImpl(
                saveGameDao = database.saveGameDao(),
                dndApi = dndApi,
            )
        )
    }
}