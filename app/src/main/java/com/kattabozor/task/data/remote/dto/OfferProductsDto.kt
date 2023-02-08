package com.kattabozor.task.data.remote.dto

import com.kattabozor.task.domain.model.Offer

data class OfferProductsDto(
    val offers: List<Offer>
)