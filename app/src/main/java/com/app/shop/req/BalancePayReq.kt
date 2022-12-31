package com.app.shop.req

data class BalancePayReq(
    val order_id: String,
    val point_type: String,
    val pay_pwd: String
)