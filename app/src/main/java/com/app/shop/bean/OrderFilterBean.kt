package com.app.shop.bean

data class OrderFilterBean(
    val detail: List<OrderFilterDetailBean>
)

data class OrderFilterDetailBean(
    val cash: String,
    val express_cost: String,
    val items: List<OrderFilterItemBean>,
    val order_id: String,
    val point: String,
    val shop_name: String
)

data class OrderFilterItemBean(
    val pic: String,
    val point: String,
    val price: String,
    val prod_count: Int,
    val prod_name: String,
    val sku_name: String
)