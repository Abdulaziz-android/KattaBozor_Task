package com.kattabozor.task.presentation.screen.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kattabozor.task.common.Resource
import com.kattabozor.task.domain.model.Offer
import com.kattabozor.task.domain.use_case.OfferUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val offerUseCases: OfferUseCases
) : ViewModel() {

    private var _getLocalOfferProductsFlow = MutableSharedFlow<Resource<Offer?>>()
    val getLocalOfferProductsFlow : SharedFlow<Resource<Offer?>> = _getLocalOfferProductsFlow.asSharedFlow()
    private var isSaved = false

    fun getSavedOffer(id:Int){
        offerUseCases.getOfferProductById(id).onEach {
            if (it is Resource.Success){
                isSaved = it.data!=null
            }
            _getLocalOfferProductsFlow.emit(it)
        }.launchIn(viewModelScope)
    }

    fun switchSavedState(offer: Offer){
        if (isSaved){
            deleteOffer(offer)
        }else{
            saveOffer(offer)
        }
    }

    private fun saveOffer(offer: Offer){
        offerUseCases.saveOfferProduct(offer).onEach {
            if (it is Resource.Success){
                getSavedOffer(offer.id)
            }
        }.launchIn(viewModelScope)
    }

    private fun deleteOffer(offer: Offer){
        offerUseCases.deleteOfferProduct(offer).onEach {
            if (it is Resource.Success){
                getSavedOffer(offer.id)
            }
        }.launchIn(viewModelScope)
    }

}