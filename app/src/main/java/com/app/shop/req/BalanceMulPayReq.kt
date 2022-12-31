package com.app.shop.req

data class BalanceMulPayReq(
    val order_ids: String,
    val pay_pwd: String,
    val point_type: String
)