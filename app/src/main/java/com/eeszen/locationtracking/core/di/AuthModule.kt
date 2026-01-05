package com.eeszen.locationtracking.core.di

import com.eeszen.locationtracking.data.repo.AuthRepo
import com.eeszen.locationtracking.data.repo.AuthRepoFakeImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class AuthModule {
    @Provides
    @Singleton
    fun provideRepo():AuthRepo{
        return AuthRepoFakeImpl()
    }
}