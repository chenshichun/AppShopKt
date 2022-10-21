package com.app.shop.bean

data class OrderIdBean(
    val detail: DetailOrderBean
)

data class DetailOrderBean(
    val items: List<DetailOrderBeanItem>,
    val order_id: String,
    val order_name: String,
    val point: Int,
    val price: String
)

data class DetailOrderBeanItem(
    val pic: String,
    val point: Int,
    val price: String,
    val prod_count: Int,
    val prod_name: String,
    val sku_name: String
)