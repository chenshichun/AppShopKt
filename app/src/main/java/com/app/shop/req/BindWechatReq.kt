package com.app.shop.req

data class BindWechatReq(
    val inv_code: String,
    val password: String,
    val phone: String,
    val sms_code: String,
    val wechat_id: String
)