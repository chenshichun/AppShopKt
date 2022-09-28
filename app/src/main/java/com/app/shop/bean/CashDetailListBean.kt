package com.app.shop.bean

data class CashDetailListBean(
    val all_rows: Int,
    val cur_rows: Int,
    val detail: List<CashDetailBean>
)
data class CashDetailBean(
    val account: String,
    val cash_out: Int,
    val channel: Int,
    val created_at: String,
    val name: String,
    val remarks: String,
    val reviewed_id: Int,
    val status: Int,
    val updated_at: String,
    val user_id: Long
)