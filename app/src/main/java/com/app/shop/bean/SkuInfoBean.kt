package com.app.shop.bean

data class SkuInfoBean(
    val sku_info: SkuInfosBean
)
data class SkuInfosBean(
    val point: String,
    val price: String,
    val prod_id: String,
    val properties: String,
    val sales: Int,
    val sku_id: String,
    val stocks: Int
)