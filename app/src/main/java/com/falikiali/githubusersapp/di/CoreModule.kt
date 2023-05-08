package com.falikiali.githubusersapp.di

import android.app.Application
import com.falikiali.githubusersapp.data.remote.Network
import com.falikiali.githubusersapp.data.remote.api.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object CoreModule {

    @Provides
    fun provideNetworkService(application: Application): ApiService {
        return Network.retrofitClient().create(ApiService::class.java)
    }

}