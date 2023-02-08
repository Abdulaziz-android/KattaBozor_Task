package com.kattabozor.task.domain.use_case

data class OfferUseCases(
    val fetchOfferProducts: FetchOfferProducts,
    val saveOfferProduct: SaveOfferProduct,
    val deleteOfferProduct: DeleteOfferProduct,
    val getOfferProductById: GetOfferProductById
)
