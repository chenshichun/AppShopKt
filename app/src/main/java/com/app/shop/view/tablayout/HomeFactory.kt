package com.app.shop.view.tablayout

import com.app.shop.base.BaseFragment
import com.app.shop.view.tablayout.HomeFactory
import com.app.shop.ui.fragment.HomeFragment
import com.app.shop.ui.fragment.ClassificationFragment
import com.app.shop.ui.fragment.ShopFragment
import com.app.shop.ui.fragment.CartFragment
import com.app.shop.ui.fragment.MineFragment
import java.util.LinkedHashMap

/**
 * @author chenshichun
 * 创建日期：2022/8/1
 * 描述：
 */
object HomeFactory {
    private val mCaches: MutableMap<Int, BaseFragment<*, *>?> = LinkedHashMap()
    fun getFragment(position: Int): BaseFragment<*, *>? {
        var fragment = mCaches[position]

        // 判断缓存中是否有
        if (fragment != null) {
            return fragment
        }
        when (position) {
            0 -> fragment = HomeFragment()
            1 -> fragment = ClassificationFragment()
            2 -> fragment = ShopFragment()
            3 -> fragment = CartFragment()
            4 -> fragment = MineFragment()
        }

        // 存储到缓存
        mCaches[position] = fragment
        return fragment
    }

    fun clearAllCache() {
        mCaches.clear()
    }
}