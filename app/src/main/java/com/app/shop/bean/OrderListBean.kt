package com.app.shop.bean

data class OrderListBean(
    val all_rows: Int,
    val cur_rows: Int,
    val orders: List<Order>
)

data class Order(
    val actual_total: String,
    val addr_order_id: String,
    val cancel_time: String,
    val close_type: Int,
    val created_at: String,
    val dvy_flow_id: String,
    val dvy_id: String,
    val dvy_time: String,
    val dvy_type: String,
    val finally_time: String,
    val freight_amount: Int,
    val is_deleted: Boolean,
    val is_payed: Int,
    val items: List<Item>,
    val order_id: String,
    val order_number: String,
    val order_type: Int,
    val pay_time: String,
    val pay_type: Int,
    val point: String,
    val prod_name: String,
    val product_nums: Int,
    val reduce_amount: Int,
    val refund_sts: Int,
    val remarks: String,
    val shop_id: String,
    val shop_name: String,
    val status: Int,
    val status_text: String,
    val total: String,
    val updated_at: String,
    val user_id: String,
    val verify_code: String,
    val delivery_cost: String,
    val service_cost: String,
    val isAppointment: Boolean,
)

data class Item(
    val basket_date: String,
    val comm_sts: Int,
    val created_at: String,
    val distribution_card_no: String,
    val order_item_id: String,
    val order_number: String,
    val pic: String,
    val point: String,
    val price: String,
    val prod_count: Int,
    val prod_id: String,
    val prod_name: String,
    val product_total_amount: String,
    val rec_time: String,
    val shop_id: String,
    val sku_id: String,
    val sku_name: String,
    val updated_at: String,
    val user_id: String
)
