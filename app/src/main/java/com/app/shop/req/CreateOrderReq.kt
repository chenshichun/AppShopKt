package com.app.shop.req

data class CreateOrderReq(
    val addr_id: String?,
    val count: Int,
    val prod_id: String?,
    val prod_name: String?,
    val remark: String?,
    val shop_id: String?,
    val sku_id: String?,
    val delivery_type: String?
    )