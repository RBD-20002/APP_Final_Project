package com.example.finalproject.data.di

import android.content.Context
import com.example.finalproject.data.local.datastrore.SessionDataStore
import com.example.finalproject.data.local.datastrore.sessionDataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {

    @Provides
    @Singleton
    fun provideSessionDataStore(
        @ApplicationContext context: Context
    ): SessionDataStore {
        return SessionDataStore(context.sessionDataStore)
    }
}