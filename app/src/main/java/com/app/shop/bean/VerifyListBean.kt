package com.app.shop.bean

data class VerifyListBean(
    val all_rows: Int,
    val cur_rows: Int,
    val orders: List<VerifyBean>
)

data class VerifyBean(
    val actual_total: Int,
    val addr_order_id: String,
    val cancel_time: String,
    val close_type: Int,
    val created_at: String,
    val dvy_flow_id: String,
    val dvy_id: Int,
    val dvy_time: String,
    val dvy_type: String,
    val finally_time: String,
    val freight_amount: Int,
    val is_deleted: Boolean,
    val is_payed: Int,
    val items: List<VerifyItemBean>,
    val order_id: String,
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
    val shop_id: String,
    val shop_name: String,
    val status: Int,
    val shop_phone: String,
    val status_text: String,
    val total: String,
    val updated_at: String,
    val user_id: String
)

data class VerifyItemBean(
    val basket_date: String,
    val comm_sts: Int,
    val created_at: String,
    val distribution_card_no: String,
    val order_item_id: Long,
    val order_number: String,
    val pic: String,
    val point: Int,
    val price: Int,
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