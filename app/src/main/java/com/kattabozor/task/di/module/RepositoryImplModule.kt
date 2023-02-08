package com.kattabozor.task.di.module

import com.kattabozor.task.data.local.dao.OfferDao
import com.kattabozor.task.data.remote.api.ProductApi
import com.kattabozor.task.data.repository.ProductRepositoryImpl
import com.kattabozor.task.domain.repository.ProductRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryImplModule {

    @Provides
    @Singleton
    fun provideProductRepository(productApi: ProductApi, offerDao: OfferDao) : ProductRepository{
        return ProductRepositoryImpl(productApi, offerDao)
    }

}