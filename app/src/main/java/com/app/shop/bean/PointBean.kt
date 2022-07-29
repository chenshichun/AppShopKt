package com.app.shop.bean

data class PointBean(
    val pl: List<PlBean>,
    val point: String,
    val rows: Int
)
data class PlBean(
    val created_at: String,
    val points: Int,
    val remark: String,
    val source: String,
    val updated_at: String,
    val userid: Long
)
