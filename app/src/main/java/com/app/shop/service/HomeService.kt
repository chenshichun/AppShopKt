package com.app.shop.service

import com.app.shop.bean.BaseNetModel
import com.app.shop.bean.HotSearchBean
import com.app.shop.bean.UserDataBean
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

/**
 * @author chenshichun
 * 创建日期：2022/7/15
 * 描述：
 *
 */
interface HomeService {
    @GET("user/myinfo")
    suspend fun getMyInfo():Response<BaseNetModel<UserDataBean>>
}