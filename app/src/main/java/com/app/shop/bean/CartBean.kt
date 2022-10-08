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
    val cart_id: String,
    val count: Int,
    val created_at: String,
    val discount_id: String,
    val distribution_card_no: String,
    val img: String,
    val is_valid: Boolean,
    val point: String,
    val price: String,
    val prod_id: String,
    val prod_name: String,
    val shop_id: String,
    val sku_id: String,
    val sku_name: String,
    val updated_at: String,
    val user_id: String,
    var isCheck: Boolean
)