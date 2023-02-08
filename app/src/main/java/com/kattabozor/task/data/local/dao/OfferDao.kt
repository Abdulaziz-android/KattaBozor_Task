package com.kattabozor.task.data.local.dao

import androidx.room.*
import com.kattabozor.task.domain.model.Offer

@Dao
interface OfferDao {

    @Query("select * from offer")
    suspend fun getSavedOffers() : List<Offer>

    @Query("select * from offer where id = :id")
    suspend fun getOfferById(id: Int) : Offer?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(offer: Offer) : Long

    @Delete
    fun deleteOffer(offer: Offer) : Int

    suspend fun updateSavedOffers(list: List<Offer>){
        val savedList = getSavedOffers()
        savedList.forEach Current@{savedOffer ->
            list.forEach { currentOffer ->
                if (savedOffer.id==currentOffer.id){
                    return@Current
                }
            }
            deleteOffer(savedOffer)
        }
    }

}