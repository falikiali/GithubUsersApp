package com.falikiali.githubusersapp.di

import com.falikiali.githubusersapp.data.ImplUserRepository
import com.falikiali.githubusersapp.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideUserRepository(implUserRepository: ImplUserRepository): UserRepository

}