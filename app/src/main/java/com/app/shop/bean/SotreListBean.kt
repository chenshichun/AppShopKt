package com.app.shop.bean

data class SotreListBean(
    val store_list: List<Store>
)
data class Store(
    val created_at: String,
    val products: List<Product>,
    val store_id: Long,
    val store_name: String,
    val store_pic: String,
    val updated_at: String,
    val user_id: Long
)

data class Product(
    val brief: String,
    val category_id: Int,
    val content: List<String>,
    val create_time: String,
    val created_at: String,
    val delivery_mode: String,
    val delivery_template_id: Int,
    val imgs: List<String>,
    val is_featured: Boolean,
    val is_home: Boolean,
    val is_locked: Boolean,
    val is_recommend: Boolean,
    val ori_point: Int,
    val ori_price: Int,
    val pic: String,
    val point: Int,
    val price: Double,
    val prod_id: Long,
    val prod_name: String,
    val prod_type: Int,
    val putaway_time: String,
    val shop_id: Long,
    val sold_num: Int,
    val status: Int,
    val total_stocks: Int,
    val update_time: String,
    val updated_at: String,
    val version: Int,
    val views_count: Int
)