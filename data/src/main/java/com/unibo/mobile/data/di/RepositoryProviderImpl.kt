package com.unibo.mobile.data.di

import android.content.Context
import androidx.room.Room
import com.unibo.mobile.data.local.db.AppDatabase
import com.unibo.mobile.data.remote.api.RetrofitClient
import com.unibo.mobile.data.repositories.ApiRepositoryImpl
import com.unibo.mobile.data.repositories.LocalRepositoryImpl
import com.unibo.mobile.domain.di.RepositoryProvider
import com.unibo.mobile.domain.repositories.ApiRepository
import com.unibo.mobile.domain.repositories.LocalRepository

class RepositoryProviderImpl(context: Context) : RepositoryProvider {

    private val database: AppDatabase = Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        "dungeon_db"
    ).build()

    private val retrofitClient = RetrofitClient()

    override val localRepository: LocalRepository =
        LocalRepositoryImpl(database.saveGameDao())

    override val apiRepository: ApiRepository =
        ApiRepositoryImpl(retrofitClient.dndService, localRepository)
}