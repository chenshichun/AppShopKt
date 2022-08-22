package com.app.shop.ui.activity

import com.app.shop.R
import com.app.shop.base.BaseActivity
import com.app.shop.bean.WithdrawPriceBean
import com.app.shop.databinding.ActivityWithdrawalAccountBinding
import com.app.shop.ui.contract.WithdrawalAccountContract
import com.app.shop.ui.presenter.WithdrawalAccountPresenter
import com.gyf.immersionbar.ktx.immersionBar

/*
* 提现账号
* */
class WithdrawalAccountActivity :
    BaseActivity<ActivityWithdrawalAccountBinding, WithdrawalAccountPresenter>(),
    WithdrawalAccountContract.View {
    override fun getPresenter(): WithdrawalAccountPresenter {
        return WithdrawalAccountPresenter()
    }

    override fun initView() {
        immersionBar {
            statusBarColor(R.color.white)
            statusBarDarkFont(true)
        }
        binding.viewHead.tvTitle.text = "提现账号"

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