package com.falikiali.githubusersapp.di

import com.falikiali.githubusersapp.domain.usecase.ImplUserUseCase
import com.falikiali.githubusersapp.domain.usecase.UserUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class AppModule {

    @Binds
    @ViewModelScoped
    abstract fun provideUserUseCase(implUserUseCase: ImplUserUseCase): UserUseCase

}