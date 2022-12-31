package com.app.shop.adapter

import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter

/**
 * @author chenshichun
 * 创建日期：2022/7/12
 * 描述：
 */
class BannerAdapter(private val viewList: List<View>) : PagerAdapter() {
    private val cacheCount = 3

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        super.destroyItem(container, position, `object`)
        if (viewList.size > cacheCount) {
            container.removeView(viewList[position % viewList.size])
        }
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val parent = viewList[position % viewList.size].parent as ViewGroup
        parent.removeView(viewList[position % viewList.size])
        container.addView(viewList[position % viewList.size])
        return viewList[position % viewList.size]
    }

    override fun getCount(): Int {
        return viewList.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }
}