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
    val basket_id: Long,
    val created_at: String,
    val discount_id: Any,
    val distribution_card_no: String,
    val prod_id: Long,
    val shop_id: Long,
    val sku_id: Int,
    val updated_at: String,
    val user_id: Long,
    var isCheck: Boolean
)