package com.app.shop.ui.activity

import com.app.shop.R
import com.app.shop.base.BaseActivity
import com.app.shop.databinding.ActivityWriteOffOrderBinding
import com.app.shop.ui.contract.WriteOffOrderContract
import com.app.shop.ui.presenter.WriteOffOrderPresenter
import com.gyf.immersionbar.ktx.immersionBar

/*
* 核销订单
* */
class WriteOffOrderActivity : BaseActivity<ActivityWriteOffOrderBinding, WriteOffOrderPresenter>(),
    WriteOffOrderContract.View {
    override fun getPresenter(): WriteOffOrderPresenter {
        return WriteOffOrderPresenter()
    }

    override fun initView() {
        immersionBar {
            statusBarColor(R.color.white)
            statusBarDarkFont(true)
        }

        binding.viewHead.tvTitle.text = "核销订单"
        binding.viewHead.ivBack.setOnClickListener {
            finish()
        }    }

    override fun showLoading() {
        showLoadingDialog()
    }

    override fun hideLoading() {
        closeLoadingDialog()
    }
}