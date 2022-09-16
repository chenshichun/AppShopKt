package com.app.shop.bean

data class CartBean(
    val list: List<ShopBean>
)

data class ShopBean(
    val prods: List<ProdCartBean>,
    val shop_id: Long,
    val shop_logo: String,
    val shop_name: String,
    var isCheck: Boolean
)

data class ProdCartBean(
    val created_at: String,
    val updated_at: String,
    val shop_id: String,
    val prod_id: String,
    val sku_id: String,
    val user_id: String,
    val count: Int,
    val discount_id: String,
    val distribution_card_no: String,
    val cart_id: String,
    val price: String,
    var point: Int,
    var isCheck: Boolean
)