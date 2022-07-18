package com.app.shop.retrofit

import com.app.shop.bean.NoBodyModel
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type

/**
 * @author chenshichun
 * 创建日期：2022/7/15
 * 描述：代替gson converter转换无响应体的response
 */
class NobodyConverterFactory private constructor() : Converter.Factory() {
    //将响应对象responseBody转成目标类型对象(也就是Call里给定的类型)
    override fun responseBodyConverter(
        type: Type, annotations: Array<Annotation?>?,
        retrofit: Retrofit?
    ): Converter<ResponseBody?, NoBodyModel?>? {
        //判断当前的类型是否是我们需要处理的类型
        return if (NoBodyModel::class.java == type) {
            //是则创建一个Converter返回转换数据
            Converter<ResponseBody?, NoBodyModel?> { value: ResponseBody? -> null } as Converter<ResponseBody?, NoBodyModel?>
        } else null
    }

    //其它两个方法我们不需要使用到.所以不需要重写.
    override fun requestBodyConverter(
        type: Type?,
        parameterAnnotations: Array<Annotation?>?,
        methodAnnotations: Array<Annotation?>?,
        retrofit: Retrofit?
    ): Converter<*, RequestBody>? {
        return null
    }

    override fun stringConverter(
        type: Type?,
        annotations: Array<Annotation?>?,
        retrofit: Retrofit?
    ): Converter<*, String>? {
        return null
    }

    companion object {
        fun create(): NobodyConverterFactory {
            return NobodyConverterFactory()
        }
    }
}