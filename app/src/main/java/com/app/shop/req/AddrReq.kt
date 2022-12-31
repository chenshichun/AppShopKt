package com.app.shop.req

data class AddrReq(
    val addr_id: String,
    val receiver: String,
    val province: String,
    val city: String,
    val area: String,
    val addr: String,
    val mobile: String,
    val is_default: Boolean
    )