package com.falikiali.githubusersapp.di

import android.app.Application
import androidx.room.Room
import com.falikiali.githubusersapp.data.local.AppDatabase
import com.falikiali.githubusersapp.data.local.dao.UserFavoriteDao
import com.falikiali.githubusersapp.data.remote.Network
import com.falikiali.githubusersapp.data.remote.api.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CoreModule {

    @Singleton
    @Provides
    fun provideNetworkService(): ApiService {
        return Network.retrofitClient().create(ApiService::class.java)
    }

    @Singleton
    @Provides
    fun provideAppDatabase(application: Application): AppDatabase {
        return Room.databaseBuilder(
            application,
            AppDatabase::class.java,
            "app_database"
        ).fallbackToDestructiveMigration().build()
    }

    @Singleton
    @Provides
    fun provideUserFavoriteDao(appDatabase: AppDatabase): UserFavoriteDao {
        return appDatabase.userFavoriteDao()
    }

}