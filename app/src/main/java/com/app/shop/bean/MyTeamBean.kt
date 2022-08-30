package com.app.shop.bean

data class MyTeamBean(
    val my_info: MyInfo
)

data class MyInfo(
    val all_count: Int,
    val all_valid_count: Int,
    val dt_count: Int,
    val dt_valid_count: Int,
    val level: String,
    val nick: String,
    val phone: String
)