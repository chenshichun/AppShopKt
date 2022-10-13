package com.app.shop.bean

data class OrderDetailBean(
    val detail: OrderDataBean
)

data class OrderDataBean(
    val actual_total: String,
    val addr: OrderAddrBean,
    val addr_order_id: String,
    val cancel_time: String,
    val close_type: Int,
    val created_at: String,
    val dvy_flow_id: String,
    val dvy_id: String,
    val dvy_time: String,
    val dvy_type: String,
    val finally_time: String,
    val freight_amount: String,
    val is_deleted: Boolean,
    val is_payed: Int,
    val items: List<OrderItem>,
    val order_id: String,
    val order_number: String,
    val order_type: Int,
    val pay_time: String,
    val pay_type: Int,
    val point: Int,
    val prod_name: String,
    val product_nums: Int,
    val reduce_amount: String,
    val refund_sts: Int,
    val remarks: String,
    val shop: Shop,
    val shop_id: Long,
    val status: Int,
    val total: String,
    val updated_at: String,
    val user_id: String,
    val verify_code: String
)

data class OrderItem(
    val basket_date: String,
    val comm_sts: Int,
    val created_at: String,
    val distribution_card_no: String,
    val order_item_id: String,
    val order_number: String,
    val pic: String,
    val point: Int,
    val price: String,
    val prod_count: Int,
    val prod_id: String,
    val prod_name: String,
    val product_total_amount: Int,
    val rec_time: String,
    val shop_id: String,
    val sku_id: String,
    val sku_name: String,
    val updated_at: String,
    val user_id: String
)

data class OrderAddrBean(
    val addr: String,
    val addr_id: String,
    val area: String,
    val area_id: String,
    val city: String,
    val city_id: String,
    val created_at: String,
    val is_default: Boolean,
    val mobile: String,
    val post_code: String,
    val province: String,
    val province_id: String,
    val `receiver`: String,
    val status: Int,
    val updated_at: String,
    val user_id: String
)