package com.app.shop.req

data class CommentReq(
    val content: String,
    val order_id: String,
    val pics: String,
    val score: String
)