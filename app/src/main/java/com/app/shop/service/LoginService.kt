package com.app.shop.service

import com.app.shop.bean.BaseNetModel
import com.app.shop.bean.UserDataBean
import com.app.shop.req.RegisterReq
import com.app.shop.req.SmsReq
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * @author chenshichun
 * 创建日期：2022/7/15
 * 描述：
 *
 */
interface LoginService {
    @POST("user/login/sms")
    suspend fun smsLogin(@Body smsReq: SmsReq): Response<BaseNetModel<UserDataBean>>

    @POST("user/register/sms")
    suspend fun smsRegister(@Body registerReq: RegisterReq): Response<BaseNetModel<Any>>
}