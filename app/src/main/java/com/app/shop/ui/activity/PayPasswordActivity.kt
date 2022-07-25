package com.app.shop.ui.activity

import com.app.shop.R
import com.app.shop.base.BaseActivity
import com.app.shop.databinding.ActivityPayPasswordBinding
import com.app.shop.ui.contract.PayPasswordContract
import com.app.shop.ui.presenter.PayPasswordPresenter
import com.gyf.immersionbar.ktx.immersionBar

/**
 * @author chenshichun
 * 创建日期：2022/7/25
 * 描述：支付密码
 *
 */
class PayPasswordActivity : BaseActivity<ActivityPayPasswordBinding, PayPasswordPresenter>(),
    PayPasswordContract.View {
    override fun getPresenter(): PayPasswordPresenter {
        return PayPasswordPresenter()
    }

    override fun initView() {
        immersionBar {
            statusBarColor(R.color.white)
            statusBarDarkFont(true)
        }

        binding.viewHead.tvTitle.text = "支付密码"
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