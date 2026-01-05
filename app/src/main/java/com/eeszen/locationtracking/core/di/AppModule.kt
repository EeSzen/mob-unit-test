package com.eeszen.locationtracking.core.di

import com.eeszen.locationtracking.data.repo.AuthRepo
import com.eeszen.locationtracking.data.repo.AuthRepoFakeImpl
import com.eeszen.locationtracking.data.repo.IUserRepo
import com.eeszen.locationtracking.data.repo.UserRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideUserRepo(): IUserRepo {
        return UserRepo()
    }
}