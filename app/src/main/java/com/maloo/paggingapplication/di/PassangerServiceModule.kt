package com.maloo.paggingapplication.di

import com.maloo.paggingapplication.data.api.PassangerService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PassangerServiceModule {
    @Singleton
    @Provides
    fun providePassangerService(retrofit: Retrofit): PassangerService =
        retrofit.create(PassangerService::class.java)
}