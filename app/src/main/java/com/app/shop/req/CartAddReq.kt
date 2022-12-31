package com.app.shop.req

data class CartAddReq(
    val bid: String,
    val count: Int,
    val gid: String,
    val sku: String
)