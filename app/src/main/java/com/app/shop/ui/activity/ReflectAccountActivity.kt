package com.app.shop.ui.activity

import android.text.Editable
import com.app.shop.R
import com.app.shop.base.BaseActivity
import com.app.shop.databinding.ActivityReflectAccountBinding
import com.app.shop.manager.AccountManager
import com.app.shop.ui.contract.ReflectAccountContract
import com.app.shop.ui.presenter.ReflectAccountPresenter
import com.gyf.immersionbar.ktx.immersionBar

/**
 * @author chenshichun
 * 创建日期：2022/7/13
 * 描述：提现账号
 */
class ReflectAccountActivity :
    BaseActivity<ActivityReflectAccountBinding, ReflectAccountPresenter>(),
    ReflectAccountContract.View {
    override fun getPresenter(): ReflectAccountPresenter {
        return ReflectAccountPresenter()
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

        binding.etAccount.text =
            Editable.Factory.getInstance().newEditable(AccountManager.getAccountInfo()!!.phone)
    }

    override fun showLoading() {
        showLoadingDialog()
    }

    override fun hideLoading() {
        closeLoadingDialog()
    }
}