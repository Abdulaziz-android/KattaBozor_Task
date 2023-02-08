package com.kattabozor.task.presentation.screen.offer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kattabozor.task.common.Resource
import com.kattabozor.task.domain.model.Offer
import com.kattabozor.task.domain.use_case.OfferUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class OfferViewModel @Inject constructor(
    private val offerUseCases: OfferUseCases
) : ViewModel() {

    private var _remoteOfferProductsFlow = MutableSharedFlow<Resource<List<Offer>>>()
    val remoteOfferProductsFlow : SharedFlow<Resource<List<Offer>>> = _remoteOfferProductsFlow.asSharedFlow()

    fun fetchOfferProducts(){
        offerUseCases.fetchOfferProducts().onEach {
            _remoteOfferProductsFlow.emit(it)
        }.launchIn(viewModelScope)
    }

}