package com.app.shop.ui.activity

import com.app.shop.base.BaseActivity
import com.app.shop.databinding.ActivityOrderDetailBinding
import com.app.shop.ui.contract.OrderDetailContract
import com.app.shop.ui.presenter.OrderDetailPresenter

/*
* 订单详情
* */
class OrderDetailActivity : BaseActivity<ActivityOrderDetailBinding, OrderDetailPresenter>(),
    OrderDetailContract.View {
    override fun getPresenter(): OrderDetailPresenter {
        return OrderDetailPresenter()
    }

    override fun initView() {

    }

    override fun showLoading() {
        showLoadingDialog()
    }

    override fun hideLoading() {
        closeLoadingDialog()
    }
}