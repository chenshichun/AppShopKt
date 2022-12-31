package com.app.shop.req

data class ServiceApplyReq(
    val addr: String,
    val area: String,
    val city: String,
    val contact: String,
    val mobile: String,
    val province: String
)