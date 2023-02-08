package com.kattabozor.task.data.remote.api

import com.kattabozor.task.data.remote.dto.OfferProductsDto
import retrofit2.http.GET

interface ProductApi {

    @GET("offers")
    suspend fun getProducts() : OfferProductsDto

}