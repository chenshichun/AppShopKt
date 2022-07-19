package com.app.shop.retrofit

import android.os.Build
import androidx.annotation.RequiresApi
import com.app.shop.manager.AccountManager
import com.app.shop.manager.ApiException
import com.app.shop.manager.HttpStatus
import com.google.gson.Gson
import com.google.gson.TypeAdapter
import com.orhanobut.logger.Logger
import okhttp3.ResponseBody
import retrofit2.Converter
import java.io.*
import java.nio.charset.StandardCharsets

class CustomGsonResponseBodyConverter<T>() :
    Converter<ResponseBody, T> {

    private var gson: Gson? = null
    private var adapter: TypeAdapter<T>? = null

    constructor(gson: Gson, adapter: TypeAdapter<T>) : this() {
        this.gson = gson
        this.adapter = adapter
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Throws(ApiException::class)
    override fun convert(value: ResponseBody): T {
        val response = value.string()
        val httpStatus = gson!!.fromJson(response, HttpStatus::class.java)
        if (httpStatus.isCodeInvalid) {
            if (httpStatus.isNeedLogin && AccountManager.isLogin()) {
                AccountManager.signOut()
            }
            value.close()
            Logger.d(httpStatus.code)
            Logger.d(httpStatus.msg)

            //throw ApiException(httpStatus.code, httpStatus.msg)
        }
        val contentType = value.contentType()
        val charset =
            if (contentType != null) contentType.charset(StandardCharsets.UTF_8) else StandardCharsets.UTF_8
        val inputStream: InputStream = ByteArrayInputStream(response.toByteArray())
        val reader: Reader = InputStreamReader(inputStream, charset)
        val jsonReader = gson!!.newJsonReader(reader)

        try {
            return adapter!!.read(jsonReader)
        } finally {
            value.close()
        }
    }
}