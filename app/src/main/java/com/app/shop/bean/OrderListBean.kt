package com.app.shop.bean

data class OrderListBean(
    val orders: List<Order>
)

data class Order(
    val actual_total: Int,
    val addr_order_id: Int,
    val cancel_time: String,
    val close_type: Int,
    val created_at: String,
    val delete_status: Int,
    val dvy_flow_id: String,
    val dvy_id: Int,
    val dvy_time: String,
    val dvy_type: String,
    val finally_time: String,
    val freight_amount: Int,
    val is_payed: Int,
    val items: List<Item>,
    val order_id: Long,
    val order_number: String,
    val order_type: Int,
    val pay_time: String,
    val pay_type: Int,
    val point: Int,
    val prod_name: String,
    val product_nums: Int,
    val reduce_amount: Int,
    val refund_sts: Int,
    val remarks: String,
    val shop_id: Long,
    val shop_name: String,
    val status: Int,
    val total: Int,
    val updated_at: String,
    val user_id: String
)

data class Item(
    val basket_date: String,
    val comm_sts: Int,
    val created_at: String,
    val distribution_card_no: String,
    val order_item_id: Long,
    val order_number: String,
    val pic: String,
    val point: String,
    val price: String,
    val prod_count: Int,
    val prod_id: Long,
    val prod_name: String,
    val product_total_amount: Int,
    val rec_time: String,
    val shop_id: Long,
    val sku_id: Long,
    val sku_name: String,
    val updated_at: String,
    val user_id: String
)
