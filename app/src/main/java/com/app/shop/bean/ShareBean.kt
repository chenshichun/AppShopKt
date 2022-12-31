package com.app.shop.bean

data class ShareBean(
    val share: Share
)

data class Share(
    val inv_img: String,
    val inv_link: String,
    val level: String,
    val posters: String,
    val rate: String,
    val inv_title: String,
    val inv_text: String,
    val tips: String

)