package com.app.shop.util

import com.alibaba.fastjson.util.TypeUtils
import com.alibaba.fastjson.JSON
import com.app.shop.bean.IntegralBean

object JsonUtils {
    init {
        TypeUtils.compatibleWithJavaBean = true
    }

    /**
     * 将json字符串转换成java对象
     * @param json
     * @param cls
     * @return
     * @throws HttpException
     */
    fun <T> jsonToBean(json: String?, cls: Class<T>?): T {
        return JSON.parseObject(json, cls)
    }

    /**
     * 将json字符串转换成java List对象
     * @param json
     * @param cls
     * @return
     * @throws HttpException
     */
    fun <T> jsonToList(json: String?, cls: Class<T>?): List<T> {
        return JSON.parseArray(json, cls)
    }

    /**
     * 将bean对象转化成json字符串
     * @param obj
     * @return
     * @throws HttpException
     */
    fun beanToJson(obj: Any?, java: Class<IntegralBean>): String {
        return JSON.toJSONString(obj)
    }
}