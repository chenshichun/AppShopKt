package com.app.shop.util

import android.content.Context
import android.os.Parcelable
import com.tencent.mmkv.MMKV


/**
 * @author chenshichun
 * 创建日期：2022/7/15
 * 描述：
 */
class MmkvUtil {
    fun put(key: String?, value: Any?) {
        val mmkv = MMKV.defaultMMKV()
        when (value) {
            is String -> {
                mmkv.encode(key, value as String?)
            }
            is Int -> {
                mmkv.encode(key, (value as Int?)!!)
            }
            is Boolean -> {
                mmkv.encode(key, (value as Boolean?)!!)
            }
            is Float -> {
                mmkv.encode(key, (value as Float?)!!)
            }
            is Long -> {
                mmkv.encode(key, (value as Long?)!!)
            }
            is Parcelable -> {
                mmkv.encode(key, (value as Parcelable?)!!)
            }
        }
    }

    operator fun get(key: String?, defaultObject: Any?): Any? {
        val mmkv = MMKV.defaultMMKV()
        when (defaultObject) {
            is String -> {
                return mmkv.decodeString(key, defaultObject as String?)
            }
            is Int -> {
                return mmkv.decodeInt(key, (defaultObject as Int?)!!)
            }
            is Boolean -> {
                return mmkv.decodeBool(key, (defaultObject as Boolean?)!!)
            }
            is Float -> {
                return mmkv.decodeFloat(key, (defaultObject as Float?)!!)
            }
            is Long -> {
                return mmkv.decodeLong(key, (defaultObject as Long?)!!)
            }
            else -> return null
        }
    }

    fun <T : Parcelable?> get(key: String?, tClass: Class<T>?): T? {
        val mmkv = MMKV.defaultMMKV()
        return mmkv.decodeParcelable(key, tClass)
    }



}