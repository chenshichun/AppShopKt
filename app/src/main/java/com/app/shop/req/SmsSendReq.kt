package com.app.shop.req

/**
 * @author chenshichun
 * 创建日期：2022/7/21
 * 描述：
 *
 */
data class SmsSendReq(
    var phone: String? = null,
    var sms_type: String? = null// login/register/findpwd/resetpwd
)