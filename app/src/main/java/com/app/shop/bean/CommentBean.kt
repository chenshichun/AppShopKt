package com.app.shop.bean

data class CommentBean(
    val avg_score:String,
    val all_rows: Int,
    val comm_info: List<CommInfo>,
    val cur_rows: Int
)

data class CommInfo(
    val content: String,
    val created_at: String,
    val evaluate: Int,
    val is_anonymous: Int,
    val order_item_id: Int,
    val pics: String,
    val postip: String,
    val prod_comm_id: Int,
    val prod_id: Long,
    val profile_pic: String,
    val rec_time: String,
    val reply_content: String,
    val reply_sts: Int,
    val reply_time: String,
    val score: Int,
    val status: Int,
    val updated_at: String,
    val useful_counts: Int,
    val user_id: String,
    val name:String
)