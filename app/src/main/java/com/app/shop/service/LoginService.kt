package com.app.shop.service

import com.app.shop.bean.BaseNetModel
import com.app.shop.bean.UserDataBean
import com.app.shop.req.PwdLoginReq
import com.app.shop.req.RegisterReq
import com.app.shop.req.SmsLoginReq
import com.app.shop.req.SmsSendReq
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
    /*
    * 验证码登录
    * */
    @POST("user/login/sms")
    suspend fun smsLogin(@Body smsLoginReq: SmsLoginReq): Response<BaseNetModel<UserDataBean>>

    /*
    * 密码登录
    * */
    @POST("user/login/pwd")
    suspend fun pwdLogin(@Body pwdLoginReq: PwdLoginReq): Response<BaseNetModel<UserDataBean>>

    /*
    * 注册
    * */
    @POST("user/register/sms")
    suspend fun smsRegister(@Body registerReq: RegisterReq): Response<BaseNetModel<Any>>

    /*
    * 发送短信
    * */
    @POST("user/sms")
    suspend fun smsCode(@Body smsSendReq: SmsSendReq): Response<BaseNetModel<Any>>
}