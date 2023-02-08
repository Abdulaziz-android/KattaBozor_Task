package com.kattabozor.task.di.module

import com.kattabozor.task.domain.repository.ProductRepository
import com.kattabozor.task.domain.use_case.*
import com.kattabozor.task.util.NetworkHelper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {

    @Provides
    @Singleton
    fun provideOfferUseCases(
        productRepository: ProductRepository,
        networkHelper: NetworkHelper
    ): OfferUseCases {
        return OfferUseCases(
            FetchOfferProducts(productRepository, networkHelper),
            SaveOfferProduct(productRepository),
            DeleteOfferProduct(productRepository),
            GetOfferProductById(productRepository)
        )
    }


}