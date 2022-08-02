package com.app.shop.ui.activity

import com.app.shop.R
import com.app.shop.base.BaseActivity
import com.app.shop.databinding.ActivityPayOrderBinding
import com.app.shop.ui.contract.PayOrderContract
import com.app.shop.ui.presenter.PayOrderPresenter
import com.gyf.immersionbar.ktx.immersionBar

/**
 * @author chenshichun
 * 创建日期：2022/8/1
 * 描述：支付订单
 */
class PayOrderActivity : BaseActivity<ActivityPayOrderBinding, PayOrderPresenter>(),
    PayOrderContract.View {
    override fun getPresenter(): PayOrderPresenter {
        return PayOrderPresenter()
    }

    override fun initView() {
        immersionBar {
            statusBarColor(R.color.white)
            statusBarDarkFont(true)
        }

        binding.viewHead.tvTitle.text = "确认订单"
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