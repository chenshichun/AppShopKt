package com.app.shop.ui.activity

import com.app.shop.R
import com.app.shop.base.BaseActivity
import com.app.shop.databinding.ActivityOrderDetailBinding
import com.app.shop.manager.Constants
import com.app.shop.ui.contract.OrderDetailContract
import com.app.shop.ui.presenter.OrderDetailPresenter
import com.gyf.immersionbar.ktx.immersionBar

/*
* 订单详情
* */
class OrderDetailActivity : BaseActivity<ActivityOrderDetailBinding, OrderDetailPresenter>(),
    OrderDetailContract.View {
    private var orderId: String = ""
    override fun getPresenter(): OrderDetailPresenter {
        return OrderDetailPresenter()
    }

    override fun initView() {
        immersionBar {
            statusBarColor(R.color.white)
            statusBarDarkFont(true)
        }

        binding.viewHead.tvTitle.text = "订单详情"
        binding.viewHead.ivBack.setOnClickListener {
            finish()
        }

        orderId = intent.getStringExtra(Constants.ORDER_ID)!!
    }

    override fun showLoading() {
        showLoadingDialog()
    }

    override fun hideLoading() {
        closeLoadingDialog()
    }
}