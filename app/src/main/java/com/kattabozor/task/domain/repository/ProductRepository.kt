package com.kattabozor.task.domain.repository

import com.kattabozor.task.data.remote.dto.OfferProductsDto
import com.kattabozor.task.domain.model.Offer

interface ProductRepository {

    suspend fun getRemoteOfferProducts() : OfferProductsDto
    suspend fun getSavedOfferById(id:Int) : Offer?

    fun saveOffer(offer: Offer) : Long
    fun deleteOffer(offer: Offer) : Int

}