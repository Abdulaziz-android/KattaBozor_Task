package com.kattabozor.task.domain.use_case

import com.kattabozor.task.R
import com.kattabozor.task.common.Resource
import com.kattabozor.task.domain.model.Offer
import com.kattabozor.task.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DeleteOfferProduct(
    private val repository: ProductRepository
) {
    operator fun invoke(offer: Offer): Flow<Resource<Int>> = flow {
        try {
            emit(Resource.Loading<Int>())
            val response = repository.deleteOffer(offer)
            emit(Resource.Success<Int>(response))
        } catch (e: Exception) {
            emit(Resource.Error<Int>(R.string.an_error_occurred))
        }
    }
}