package com.app.shop.bean.type

/**
 * @author chenshichun
 * 创建日期：2022/7/29
 * 描述：
 *
 */
enum class SortType(val sortType: String) {
    ASC_FINAL("asc_final"),
    DESC_FINAL("desc_final"),
    ASC_SOLD("asc_sold"),
    DESC_SOLD("desc_sold"),
    ASC_PRICE("asc_price"),
    DESC_PRICE("desc_price")
}