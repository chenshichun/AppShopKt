package com.app.shop.view.tablayout

import android.content.Context
import android.util.AttributeSet
import com.flyco.tablayout.CommonTabLayout
import com.flyco.tablayout.listener.OnTabSelectListener

/**
 * @author chenshichun
 * 创建日期：2022/8/1
 * 描述：
 */
class MyCommonTabLayout : CommonTabLayout {
    constructor(context: Context?) : super(context, null, 0) {}
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs, 0) {}

    var mListener: OnTabSelectListener? = null
    override fun setCurrentTab(currentTab: Int) {
        super.setCurrentTab(currentTab)
        if (mListener != null) {
            mListener!!.onTabSelect(currentTab)
        }
    }

    override fun setOnTabSelectListener(listner: OnTabSelectListener) {
        mListener = listner
    }
}