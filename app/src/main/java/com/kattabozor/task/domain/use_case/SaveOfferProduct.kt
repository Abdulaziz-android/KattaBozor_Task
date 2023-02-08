package com.kattabozor.task.domain.use_case

import com.kattabozor.task.R
import com.kattabozor.task.common.Resource
import com.kattabozor.task.domain.model.Offer
import com.kattabozor.task.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class SaveOfferProduct(
    private val repository: ProductRepository
) {
    operator fun invoke(offer: Offer): Flow<Resource<Long>> = flow {
        try {
            emit(Resource.Loading<Long>())
            val response = repository.saveOffer(offer)
            emit(Resource.Success<Long>(response))
        } catch (e: Exception) {
            emit(Resource.Error<Long>(R.string.an_error_occurred))
        }
    }
}