package com.app.shop.bean

data class UpdateBean(
    val apk_info: ApkInfo
)

data class ApkInfo(
    val apk_name: String,
    val created_at: String,
    val description: String,
    val download_url: String,
    val is_active: Boolean,
    val package_name: String,
    val publisher: String,
    val remark: String,
    val updated_at: String,
    val version_code: String,
    val version_name: String
)