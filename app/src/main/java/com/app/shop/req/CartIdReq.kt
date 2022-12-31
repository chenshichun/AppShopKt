package com.app.shop.req

data class CartIdReq(
    val cart_id: String,
    val delivery_type:String,
    val addr_id:String?,
    val remark:String
    )