package com.app.shop.view.pop

import android.content.Context
import android.widget.TextView
import com.app.shop.R
import com.lxj.xpopup.core.BottomPopupView

/**
 * @author chenshichun
 * 创建日期：2022/7/27
 * 描述：
 *
 */
class SpecificationPop(context: Context) : BottomPopupView(context) {
    lateinit var tvBuy: TextView
    override fun getImplLayoutId(): Int {
        return R.layout.pop_specification
    }

    override fun onCreate() {
        super.onCreate()
        tvBuy = findViewById(R.id.tv_buy)
        tvBuy.setOnClickListener {

        }
    }
}