package com.kattabozor.task.data.local.convertor

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.kattabozor.task.domain.model.Attribute
import java.lang.reflect.Type

class AttributeListConvertor {

    @TypeConverter
    fun fromAttributeList(list: List<Attribute?>?): String? {
        if (list == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<List<Attribute?>?>() {}.type
        return gson.toJson(list, type)
    }


    @TypeConverter
    fun toAttributeList(string: String?): List<Attribute>? {
        if (string == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<List<Attribute?>?>() {}.type
        return gson.fromJson<List<Attribute>>(string, type)
    }
}
