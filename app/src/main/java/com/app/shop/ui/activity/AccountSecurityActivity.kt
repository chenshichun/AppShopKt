package com.app.shop.ui.activity

import com.app.shop.R
import com.app.shop.base.BaseActivity
import com.app.shop.databinding.ActivityAccountSecurityBinding
import com.app.shop.ui.contract.AccountSecurityContract
import com.app.shop.ui.presenter.AccountSecurityPresenter
import com.gyf.immersionbar.ktx.immersionBar

class AccountSecurityActivity :
    BaseActivity<ActivityAccountSecurityBinding, AccountSecurityPresenter>(),
    AccountSecurityContract.View {
    override fun getPresenter(): AccountSecurityPresenter {
        return AccountSecurityPresenter()
    }

    override fun initView() {
        immersionBar {
            statusBarColor(R.color.white)
            statusBarDarkFont(true)
        }

        binding.viewHead.tvTitle.text = "账号安全"
        binding.viewHead.ivBack.setOnClickListener {
            finish()
        }
    }

    override fun showLoading() {
    }

    override fun hideLoading() {
    }
}