package com.kattabozor.task.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.kattabozor.task.data.local.convertor.AttributeListConvertor
import com.kattabozor.task.data.local.convertor.ImageConvertor
import com.kattabozor.task.data.local.dao.OfferDao
import com.kattabozor.task.domain.model.Offer

@Database(entities = [Offer::class], version = 1)
@TypeConverters(
    AttributeListConvertor::class,
    ImageConvertor::class
)
abstract class AppDatabase : RoomDatabase() {

    abstract val offerDao: OfferDao


    companion object {
        const val DATABASE = "task_kb_db"
    }
}