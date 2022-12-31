package com.app.shop.req

data class SetPwdReq(
    val password: String,
    val phone: String,
    val sms_code: String
)