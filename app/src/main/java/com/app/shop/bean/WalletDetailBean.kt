package com.app.shop.bean

data class WalletDetailBean(
    val all_rows: Int,
    val cur_rows: Int,
    val detail: List<DetailBean>
)

data class DetailBean(
    val cash: String,
    val created_at: String,
    val remarks: String,
    val source: String,
    val updated_at: String,
    val user_id: String
)