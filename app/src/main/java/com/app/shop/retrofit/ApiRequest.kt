package com.app.shop.retrofit

import com.app.shop.base.BaseConstant
import com.orhanobut.logger.Logger
import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

/**
 * @author chenshichun
 * 创建日期：2022/7/15
 * 描述：
 *
 */
class ApiRequest private constructor() {
    private val retrofit: Retrofit

    companion object {
        private var instance: ApiRequest? = null
            get() {
                if (field == null) {
                    field = ApiRequest()
                }
                return field
            }

        fun <T> create(service: Class<T>?): T {
            return instance!!.retrofit.create(service)
        }
    }

    init {
        val client: OkHttpClient
        val mNetworkInterceptor = NetworkInterceptor()
        val dispatcher = Dispatcher(Executors.newFixedThreadPool(20))
        dispatcher.maxRequests = 20
        dispatcher.maxRequestsPerHost = 1
        val logInterceptor = HttpLoggingInterceptor(HttpLogger())
        logInterceptor.level = HttpLoggingInterceptor.Level.BODY
        client = OkHttpClient.Builder()
            .dispatcher(dispatcher) //.addInterceptor(new OkHttpInterceptor())
            .addInterceptor(mNetworkInterceptor)
            .addInterceptor(logInterceptor)
            .retryOnConnectionFailure(true)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .connectTimeout(30, TimeUnit.SECONDS)
            .build()
        retrofit = Retrofit.Builder()
            .baseUrl(BaseConstant.HOST)
            .addConverterFactory(NobodyConverterFactory.create())
            .addConverterFactory(CustomGsonConverterFactory.create())
            .client(client)
            .build()
    }

    private inner class HttpLogger : HttpLoggingInterceptor.Logger {
        private val mMessage = StringBuilder()
        override fun log(message: String) {
            // 请求或者响应开始s
            var message = message
            if (message.startsWith("--> POST") || message.startsWith("--> GET") || message.startsWith(
                    "--> DELETE"
                ) || message.startsWith("--> PUT")
            ) {
                mMessage.setLength(0)
            }
            // 以{}或者[]形式的说明是响应结果的json数据，需要进行格式化
            if (message.startsWith("{") && message.endsWith("}")
                || message.startsWith("[") && message.endsWith("]")
            ) {
                message = JsonUtil().formatJson(message)
            }
            mMessage.append(
                """
                    $message
                    
                    """.trimIndent()
            )
            // 请求或者响应结束，打印整条日志
            if (message.startsWith("<-- END HTTP")) {
                Logger.e(mMessage.toString())
            }
        }
    }
}
