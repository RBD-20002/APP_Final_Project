package com.example.finalproject.data.di

import com.example.finalproject.data.local.datastrore.SessionDataStore
import com.example.finalproject.data.remote.api.AuthApiService
import com.example.finalproject.data.remote.api.CategoryApiService
import com.example.finalproject.data.remote.api.ProductApiService
import com.example.finalproject.data.repository.auth.AuthRepository
import com.example.finalproject.data.repository.auth.AuthRepositoryImpl
import com.example.finalproject.data.repository.category.CategoryRepository
import com.example.finalproject.data.repository.category.CategoryRepositoryImpl
import com.example.finalproject.data.repository.product.ProductRepository
import com.example.finalproject.data.repository.product.ProductRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideAuthRepository(authApiService: AuthApiService, sessionDataStore: SessionDataStore): AuthRepository {
        return AuthRepositoryImpl(authApiService = authApiService, sessionDataStore = sessionDataStore)
    }

    @Provides
    @Singleton
    fun provideCategoryRepository(categoryApiService: CategoryApiService, sessionDataStore: SessionDataStore): CategoryRepository {
        return CategoryRepositoryImpl(categoryApiService = categoryApiService, sessionDataStore = sessionDataStore)
    }

    @Provides
    @Singleton
    fun provideProductRepository(productApiService: ProductApiService, sessionDataStore: SessionDataStore): ProductRepository {
        return ProductRepositoryImpl(productApiService = productApiService, sessionDataStore = sessionDataStore)
    }
}