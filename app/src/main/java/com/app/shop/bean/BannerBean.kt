package com.app.shop.bean

/**
 * @author chenshichun
 * 创建日期：2022/7/21
 * 描述：
 *
 */
data class BannerBean(
    val slide: List<Slide>
)

data class Slide(
    val created_at: String,
    val des: String,
    val img_id: Int,
    val img_url: String,
    val is_deleted: Boolean,
    val link: String,
    val relation: Int,
    val seq: Int,
    val shop_id: Int,
    val status: Int,
    val title: String,
    val type: Int,
    val updated_at: String,
    val upload_time: String
)