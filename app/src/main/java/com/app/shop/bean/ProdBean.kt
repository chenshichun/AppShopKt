package com.app.shop.bean

/**
 * @author chenshichun
 * 创建日期：2022/7/20
 * 描述：
 */
data class ProdBean(
    val all_rows: Int,//总条数
    val cur_rows: Int,//当前条数
    val prods: List<Prod>
)

data class Prod(
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
    val price: String,
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