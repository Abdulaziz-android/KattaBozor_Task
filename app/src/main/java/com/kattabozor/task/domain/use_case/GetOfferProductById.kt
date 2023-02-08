package com.kattabozor.task.domain.use_case

import com.kattabozor.task.R
import com.kattabozor.task.common.Resource
import com.kattabozor.task.domain.model.Offer
import com.kattabozor.task.domain.repository.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetOfferProductById(
    private val repository: ProductRepository
) {
    operator fun invoke(id:Int): Flow<Resource<Offer?>> = flow {
        try {
            emit(Resource.Loading<Offer?>())
            val offer = repository.getSavedOfferById(id)
            emit(Resource.Success<Offer?>(offer))
        } catch (e: Exception) {
            emit(Resource.Error<Offer?>(R.string.an_error_occurred))
        }
    }
}