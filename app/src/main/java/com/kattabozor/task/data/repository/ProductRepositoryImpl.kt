package com.kattabozor.task.data.repository

import com.kattabozor.task.data.local.dao.OfferDao
import com.kattabozor.task.data.remote.api.ProductApi
import com.kattabozor.task.data.remote.dto.OfferProductsDto
import com.kattabozor.task.domain.model.Offer
import com.kattabozor.task.domain.repository.ProductRepository

class ProductRepositoryImpl(
    private val productApi: ProductApi,
    private val offerDao: OfferDao
) : ProductRepository {

    override suspend fun getRemoteOfferProducts(): OfferProductsDto {
        val offerObject = productApi.getProducts()
        offerDao.updateSavedOffers(offerObject.offers)
        return offerObject
    }

    override suspend fun getSavedOfferById(id: Int): Offer? {
        return offerDao.getOfferById(id)
    }

    override fun saveOffer(offer: Offer) : Long {
        return offerDao.insert(offer)
    }

    override fun deleteOffer(offer: Offer): Int {
        return offerDao.deleteOffer(offer)
    }

}