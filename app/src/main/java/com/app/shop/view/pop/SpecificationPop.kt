package com.app.shop.view.pop

import android.content.Context
import android.widget.TextView
import androidx.viewbinding.ViewBinding
import com.app.shop.R
import com.app.shop.ui.activity.ConfirmOrderActivity
import com.app.shop.util.IntentUtil
import com.lxj.xpopup.core.BottomPopupView
import com.orhanobut.logger.Logger

/**
 * @author chenshichun
 * 创建日期：2022/7/27
 * 描述：
 *
 */
class SpecificationPop(context: Context) : BottomPopupView(context) {
    lateinit var tvBuy: TextView
    var binding: ViewBinding? = null

    override fun getImplLayoutId(): Int {
        return R.layout.pop_specification
    }

    override fun onCreate() {
        super.onCreate()
        tvBuy = findViewById(R.id.tv_buy)
        tvBuy.setOnClickListener {
            Logger.d("立即购买")
            IntentUtil.get()!!.goActivity(context, ConfirmOrderActivity::class.java)
            dismiss()
        }
    }
}