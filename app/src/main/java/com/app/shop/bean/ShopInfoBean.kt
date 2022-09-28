package com.app.shop.bean

data class ShopInfoBean(
    val shop_info: ShopPageInfo

)

data class ShopPageInfo(
    val area: String,
    val city: String,
    val create_time: String,
    val created_at: String,
    val distant: String,
    val fans: Int,
    val fixed_freight: Any,
    val full_free_shipping: Any,
    val intro: String,
    val is_distribution: Int,
    val is_locked: Boolean,
    val is_service_center: Boolean,
    val mobile: String,
    val open_time: String,
    val pca_code: String,
    val province: String,
    val remark: String,
    val sales: Int,
    val score: Int,
    val shop_address: String,
    val shop_id: Long,
    val shop_industry: Int,
    val shop_lat: String,
    val shop_lng: String,
    val shop_logo: String,
    val shop_name: String,
    val shop_notice: String,
    val shop_owner: String,
    val shop_photos: String,
    val shop_status: Int,
    val shop_type: Int,
    val tel: String,
    val transport_type: Int,
    val update_time: String,
    val updated_at: String,
    val user_id: String,
    val is_collected:Boolean
)