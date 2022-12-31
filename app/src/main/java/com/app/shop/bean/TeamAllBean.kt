package com.app.shop.bean

data class TeamAllBean(
    val team_info: TeamInfo
)

data class TeamInfo(
    val all_rows: Int,
    val cur_rows: Int,
    val list: List<ListBean>
)

data class ListBean(
    val all_count: Int,
    val all_valid_count: Int,
    val level: String,
    val nick: String,
    val phone: String
)