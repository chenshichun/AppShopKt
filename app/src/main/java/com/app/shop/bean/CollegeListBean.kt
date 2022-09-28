package com.app.shop.bean

data class CollegeListBean(
    val list: List<VideoBean>
)

data class VideoBean(
    val cover: String,
    val title: String,
    val video: String
)