package com.app.shop.bean

data class GoodsBean(
    val comment: Comment,
    val comment_count: Int,
    val prod_info: ProdInfo,
    val shop_info: ShopInfo,
    val is_collected: Boolean
)

data class Comment(
    val content: String,
    val created_at: String,
    val evaluate: Int,
    val is_anonymous: Int,
    val name: String,
    val order_item_id: Long,
    val pics: String,
    val postip: String,
    val prod_comm_id: Long,
    val prod_id: String,
    val profile_pic: String,
    val rec_time: String,
    val reply_content: String,
    val reply_sts: Int,
    val reply_time: String,
    val score: Int,
    val status: Int,
    val updated_at: String,
    val useful_counts: Int,
    val user_id: String
)

data class ProdInfo(
    val brief: String,
    val category_id: Int,
    val content: List<String>,
    val create_time: String,
    val created_at: String,
    val delivery_mode: String,
    val delivery_template_id: Int,
    val imgs: List<String>,
    val is_appt: Boolean,
    val is_featured: Boolean,
    val is_home: Boolean,
    val is_local: Boolean,
    val is_locked: Boolean,
    val is_recommend: Boolean,
    val ori_point: String,
    val ori_price: String,
    val pic: String,
    val point: String,
    val price: String,
    val prod_id: String,
    val prod_name: String,
    val prod_type: Int,
    val putaway_time: String,
    val shop_id: String,
    val sku_count: List<SkuCount>,
    val sku_info: List<SkuInfo>,
    val sold_num: Int,
    val status: Int,
    val total_stocks: Int,
    val update_time: String,
    val updated_at: String,
    val version: Int,
    val views_count: Int,
    var delivery_cost: String,// 邮费
    var service_cost: String,// 服务费
    var tags: String,
)

data class SkuInfo(
    val sku: String,
    val sku_prop: List<SkuInfoName>,
)

data class SkuInfoName(
    val name: String,
    var isCheck: Boolean,
)

data class SkuCount(
    val point: Int,
    val price: String,
    val prod_id: String,
    val properties: String,
    val sku_id: String,
    val sku_name: String,
    val stocks: Int
)

data class ShopInfo(
    val id: String,
    val name: String,
    val service_phone: String
)