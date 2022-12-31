package com.app.shop.req

data class SubmitVeriReq(
    val order_number: String,
    val point: String,
    val user_id: String
)