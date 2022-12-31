package com.app.shop.retrofit

import android.annotation.SuppressLint
import android.text.TextUtils
import com.app.shop.base.BaseApplication
import com.app.shop.base.BaseConstant
import com.app.shop.manager.AccountManager
import com.app.shop.manager.Constants
import com.app.shop.util.AppUtil
import com.app.shop.util.MmkvUtil
import com.blankj.utilcode.util.NetworkUtils
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

/**
 * @author chenshichun
 * 创建日期：2022/7/15
 * 描述：
 *
 */
class NetworkInterceptor : Interceptor {
    fun getRequest(): Request? {
        return request
    }

    fun setRequest(request: Request?) {
        this.request = request
    }

    private var request: Request? = null

    @SuppressLint("MissingPermission")
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        request = chain.request()

        val builder = chain.request().newBuilder()
        val token: String? = AccountManager.getToken()

        request = if (!TextUtils.isEmpty(token)) {
            builder
                .header(BaseConstant.ACCESS_TOKEN, "Bearer $token")
                .build()
        } else {
            builder
                .build()
        }
        return if (NetworkUtils.isAvailable()) {
            request = request!!.newBuilder()
                .removeHeader("Pragma")
                .removeHeader("Cache-Control")
                .addHeader("User-Agent", "android")
                .addHeader("X-API-VERSION",
                    BaseApplication.mContext?.let { AppUtil.getVersionCode(it) })
                .addHeader("lat", (MmkvUtil[Constants.LOCATION_LAT, "0"] as String))
                .addHeader("lng", (MmkvUtil[Constants.LOCATION_LNG, "0"] as String))
                .header(
                    "Cache-Control",
                    "public,max-age=" + CACHE_AGE_SEC
                ).method(request!!.method(), request!!.body())
                .build()
            val response = chain.proceed(request)
            response.newBuilder()
                .removeHeader("Pragma")
                .removeHeader("Cache-Control")
                .addHeader("User-Agent", "android")
                .addHeader("lat", (MmkvUtil[Constants.LOCATION_LAT, "0"] as String))
                .addHeader("lng", (MmkvUtil[Constants.LOCATION_LNG, "0"] as String))
                .addHeader("X-API-VERSION",
                    BaseApplication.mContext?.let { AppUtil.getVersionCode(it) })
                .header("Cache-Control", "public,max-age=" + CACHE_AGE_SEC)
                .build()
        } else {
            request = request!!.newBuilder()
                .removeHeader("Pragma")
                .removeHeader("Cache-Control") //
                .addHeader("User-Agent", "android")
                .addHeader("lat", (MmkvUtil[Constants.LOCATION_LAT, "0"] as String))
                .addHeader("lng", (MmkvUtil[Constants.LOCATION_LNG, "0"] as String))
                .addHeader("X-API-VERSION",
                    BaseApplication.mContext?.let { AppUtil.getVersionCode(it) })
                .header(
                    "Cache-Control",
                    "public,only-if-cached,max-stale=" + CACHE_STALE_SEC
                ).method(request!!.method(), request!!.body())
                .build()
            chain.proceed(request).newBuilder()
                .removeHeader("Pragma")
                .removeHeader("Cache-Control") //
                .addHeader("User-Agent", "android")
                .addHeader("lat", (MmkvUtil[Constants.LOCATION_LAT, "0"] as String))
                .addHeader("lng", (MmkvUtil[Constants.LOCATION_LNG, "0"] as String))
                .addHeader("X-API-VERSION",
                    BaseApplication.mContext?.let { AppUtil.getVersionCode(it) })
                .header("Cache-Control", "public,only-if-cached,max-stale=" + CACHE_STALE_SEC)
                .build()
        }
    }

    companion object {
        private const val CACHE_STALE_SEC = (60 * 60 * 24 * 7).toLong()
        private const val CACHE_AGE_SEC: Long = 0

        @Synchronized
        fun setCommonParam(commonParams: Map<String, String?>?) {
            if (commonParams != null) {
                if (Companion.commonParams != null) {
                    Companion.commonParams!!.clear()
                } else {
                    Companion.commonParams = HashMap()
                }
                for (paramKey in commonParams.keys) {
                    Companion.commonParams!![paramKey] = commonParams[paramKey]
                }
            }
        }

        @Synchronized
        fun updateOrInsertCommonParam(paramKey: String, paramValue: String) {
            if (commonParams == null) {
                commonParams = HashMap()
            }
            commonParams!![paramKey] = paramValue
        }

        private var commonParams: MutableMap<String, String?>? = null
    }
}
