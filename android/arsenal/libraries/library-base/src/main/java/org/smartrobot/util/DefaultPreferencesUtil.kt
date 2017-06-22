package org.smartrobot.util

import android.content.Context
import android.content.SharedPreferences
//import com.fasterxml.jackson.databind.ObjectMapper
//import com.fasterxml.jackson.databind.type.TypeFactory
//import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import org.smartrobot.base.DefaultBaseApplication
import java.util.*


class DefaultPreferencesUtil protected constructor() {
    private var mSharedPreferences: SharedPreferences = DefaultBaseApplication.instance.getSharedPreferences(DATA_NAME, Context.MODE_PRIVATE)
//    private var objectMapper: ObjectMapper = ObjectMapper().registerKotlinModule()

    fun getBoolean(name: String, bDefault: Boolean): Boolean {
        return mSharedPreferences.getBoolean(name, bDefault)
    }

    fun putBoolean(name: String, value: Boolean): Boolean {
        val editor = mSharedPreferences.edit()
        editor.putBoolean(name, value)
        return editor.commit()
    }

    fun putString(key: String, value: String): Boolean {
        val editor = mSharedPreferences.edit()
        editor.putString(key, value)
        return editor.commit()
    }

    fun getString(key: String): String {
        return mSharedPreferences.getString(key, "")
    }

    fun putInt(key: String, value: Int): Boolean {
        val editor = mSharedPreferences.edit()
        editor.putInt(key, value)
        return editor.commit()
    }

    fun putEntity(key: String, value: Any): Boolean {
        try {
            val editor = mSharedPreferences.edit()
//            editor.putString(key, objectMapper.writeValueAsString(value))
            return editor.commit()
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }

    }

    fun <T> getEntity(key: String, classOfT: Class<T>): T? {
        var entity: T? = null
        try {
//            entity = objectMapper.readValue(mSharedPreferences.getString(key, null), classOfT)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return entity
    }

    fun putList(key: String, list: List<*>): Boolean {
        try {
            val editor = mSharedPreferences.edit()
//            editor.putString(key, objectMapper.writeValueAsString(list))
            return editor.commit()
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }

    }

    fun <T> getList(key: String, classOfT: Class<T>?): MutableList<T> {
        var list: MutableList<T> = ArrayList()
        try {
//            list = objectMapper.readValue(mSharedPreferences.getString(key, null), TypeFactory.defaultInstance().constructCollectionType(ArrayList::class.java, classOfT))
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return list
    }

    fun getInt(key: String, iDefault: Int): Int {
        return mSharedPreferences.getInt(key, iDefault)
    }

    fun putLong(key: String, value: Long): Boolean {
        val editor = mSharedPreferences.edit()
        editor.putLong(key, value)
        return editor.commit()
    }

    fun getLong(key: String, iDefault: Long): Long {
        return mSharedPreferences.getLong(key, iDefault)
    }

    companion object {
        val DATA_NAME = "org.smartrobot.preferences"
        var instance: DefaultPreferencesUtil = DefaultPreferencesUtil()
    }
}