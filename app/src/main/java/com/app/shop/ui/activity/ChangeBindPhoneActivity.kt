package com.app.shop.ui.activity

import com.app.shop.R
import com.app.shop.base.BaseActivity
import com.app.shop.databinding.ActivityChangeBindPhoneBinding
import com.app.shop.ui.contract.ChangeBindPhoneContract
import com.app.shop.ui.presenter.ChangeBindPhonePresenter
import com.gyf.immersionbar.ktx.immersionBar

class ChangeBindPhoneActivity :
    BaseActivity<ActivityChangeBindPhoneBinding, ChangeBindPhonePresenter>(),
    ChangeBindPhoneContract.View {
    override fun getPresenter(): ChangeBindPhonePresenter {
        return ChangeBindPhonePresenter()
    }

    override fun initView() {
        immersionBar {
            statusBarColor(R.color.white)
            statusBarDarkFont(true)
        }

        binding.viewHead.tvTitle.text = "换绑手机号"
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