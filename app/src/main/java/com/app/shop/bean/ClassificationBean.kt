package com.app.shop.bean

data class ClassificationBean(
    val cate: List<CateBean>
)

data class CateBean(
    val category_id: String,
    val category_name: String,
    val children: List<ChildrenBean>,
    val created_at: String,
    val grade: Int,
    val icon: String,
    val parent_id: String,
    val pic: String,
    val rec_time: String,
    val seq: Int,
    val status: Int,
    val update_time: String,
    val updated_at: String,
    var isCheck: Boolean
)

data class ChildrenBean(
    val category_id: Int,
    val category_name: String,
    val children: List<Any>,
    val created_at: String,
    val grade: Int,
    val icon: String,
    val parent_id: Int,
    val pic: String,
    val rec_time: String,
    val seq: Int,
    val status: Int,
    val update_time: String,
    val updated_at: String
)