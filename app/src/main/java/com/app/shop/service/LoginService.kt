package com.app.shop.service

import com.app.shop.bean.BaseNetModel
import com.app.shop.bean.UserDataBean
import com.app.shop.req.*
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
    * 微信登录
    *
    */
    @POST("user/login/wechat")
    suspend fun wechatLogin(@Body wxLoginReq: WxLoginReq): Response<BaseNetModel<UserDataBean>>

    /*
    * 忘记密码
    * */
    @POST("user/setpwd/login")
    suspend fun setpwd(@Body setPwdReq: SetPwdReq): Response<BaseNetModel<Any>>

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

    /*
    * 绑定手机
    * */
    @POST("user/register/bind/wechat")
    suspend fun bindWechat(@Body bindWechatReq: BindWechatReq): Response<BaseNetModel<Any>>
}