package com.app.shop.view.tablayout

import com.flyco.tablayout.listener.CustomTabEntity

/**
 * @author chenshichun
 * 创建日期：2022/8/1
 * 描述：
 */
class TabEntity(var title: String, var selectedIcon: Int, var unSelectedIcon: Int) :
    CustomTabEntity {
    override fun getTabTitle(): String {
        return title
    }

    override fun getTabSelectedIcon(): Int {
        return selectedIcon
    }

    override fun getTabUnselectedIcon(): Int {
        return unSelectedIcon
    }
}