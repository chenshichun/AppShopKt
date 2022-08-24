package com.app.shop.ui.activity

import com.app.shop.R
import com.app.shop.base.BaseActivity
import com.app.shop.databinding.ActivityReservationOrderBinding
import com.app.shop.ui.contract.ReservationOrderContract
import com.app.shop.ui.presenter.ReservationOrderPresenter
import com.gyf.immersionbar.ktx.immersionBar

/*
* 预约订单
* */
class ReservationOrderActivity :
    BaseActivity<ActivityReservationOrderBinding, ReservationOrderPresenter>(),
    ReservationOrderContract.View {
    override fun getPresenter(): ReservationOrderPresenter {
        return ReservationOrderPresenter()
    }

    override fun initView() {
        immersionBar {
            statusBarColor(R.color.white)
            statusBarDarkFont(true)
        }

        binding.viewHead.tvTitle.text = "预约订单"
        binding.viewHead.ivBack.setOnClickListener {
            finish()
        }
    }

    override fun showLoading() {
        showLoadingDialog()
    }

    override fun hideLoading() {
        closeLoadingDialog()
    }
}