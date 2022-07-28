package com.app.shop.bean

/**
 * @author chenshichun
 * 创建日期：2022/7/28
 * 描述：
 */
enum class CategoryType(val categoryName: String) {
    RECOMMEND("为你推荐"),
    CHOSE("精选好物"),
    SELLING("热卖榜单"),
    NEW("每日上新"),
    GROUP_BUY("拼团购"),
}