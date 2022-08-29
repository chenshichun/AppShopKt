package com.app.shop.ui.activity

import com.app.shop.R
import com.app.shop.base.BaseActivity
import com.app.shop.databinding.ActivityBusinessSchoolBinding
import com.app.shop.ui.contract.BusinessSchoolContract
import com.app.shop.ui.presenter.BusinessSchoolPresenter
import com.gyf.immersionbar.ktx.immersionBar

class BusinessSchoolActivity :
    BaseActivity<ActivityBusinessSchoolBinding, BusinessSchoolPresenter>(),
    BusinessSchoolContract.View {
    override fun getPresenter(): BusinessSchoolPresenter {
        return BusinessSchoolPresenter()
    }

    override fun initView() {
        immersionBar {
            statusBarColor(R.color.white)
            statusBarDarkFont(true)
        }

        binding.viewHead.tvTitle.text = "商学院"
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