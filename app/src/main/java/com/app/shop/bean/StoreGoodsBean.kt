package com.app.shop.bean

data class StoreGoodsBean(
    val all_rows: Int,
    val cur_rows: Int,
    val prods: List<Prod>
)