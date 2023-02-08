package com.kattabozor.task.data.local.convertor

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.kattabozor.task.domain.model.Image
import java.lang.reflect.Type

class ImageConvertor {

    @TypeConverter
    fun fromImage(list: Image?): String? {
        if (list == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<Image?>() {}.type
        return gson.toJson(list, type)
    }


    @TypeConverter
    fun tImage(string: String?): Image? {
        if (string == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<Image?>() {}.type
        return gson.fromJson<Image>(string, type)
    }
}
