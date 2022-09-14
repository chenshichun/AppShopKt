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
    val basket_count: Int,
    val basket_date: String,
    val basket_id: String,
    val created_at: String,
    val discount_id: Any,
    val distribution_card_no: String,
    val prod_id: String,
    val shop_id: String,
    val sku_id: String,
    val updated_at: String,
    val user_id: String,
    var isCheck: Boolean
)