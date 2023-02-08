package com.kattabozor.task.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.kattabozor.task.data.local.convertor.AttributeListConvertor
import com.kattabozor.task.data.local.convertor.ImageConvertor

@Entity
data class Offer(
    @PrimaryKey
    val id: Int,
    @TypeConverters(AttributeListConvertor::class)
    val attributes: List<Attribute>,
    val brand: String,
    val category: String,
    @TypeConverters(ImageConvertor::class)
    val image: Image,
    val merchant: String,
    val name: String
) : java.io.Serializable