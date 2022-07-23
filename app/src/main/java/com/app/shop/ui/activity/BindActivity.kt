package com.app.shop.ui.activity

import android.view.View
import com.app.shop.R
import com.app.shop.base.BaseActivity
import com.app.shop.databinding.ActivityBindBinding
import com.app.shop.ui.contract.BindContract
import com.app.shop.ui.presenter.BindPresenter
import com.gyf.immersionbar.ktx.immersionBar

class BindActivity : BaseActivity<ActivityBindBinding, BindPresenter>(),
    BindContract.Presenter, View.OnClickListener {

    override fun getPresenter(): BindPresenter {
        return BindPresenter()
    }

    override fun initView() {
        immersionBar {
            statusBarColor(R.color.white)
            statusBarDarkFont(true)
        }
        binding.ivBack.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when (view!!.id) {
            R.id.iv_back -> finish()
        }
    }

    override fun showLoading() {
        showLoadingDialog()
    }

    override fun hideLoading() {
        closeLoadingDialog()
    }
}