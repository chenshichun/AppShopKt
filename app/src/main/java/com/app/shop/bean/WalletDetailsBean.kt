package com.app.shop.bean

data class WalletDetailsBean(
    val all_rows: Int,
    val cur_rows: Int,
    val detail: List<Detail>
)

data class Detail(
    val cash: Double,
    val created_at: String,
    val remarks: String,
    val source: String,
    val updated_at: String,
    val user_id: Long
)