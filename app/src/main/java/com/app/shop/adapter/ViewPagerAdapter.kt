package com.app.shop.adapter

import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter

/**
 * @author chenshichun
 * 创建日期：2022/8/20
 * 描述：
 *
 */
class ViewPagerAdapter(mViewList: List<View>) :
    PagerAdapter() {
    private val mViewList: List<View>
    override fun getCount(): Int {
        return mViewList.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        container.addView(mViewList[position])
        return mViewList[position]
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(mViewList[position])
    }

    init {
        this.mViewList = mViewList
    }
}
