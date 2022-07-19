package com.app.shop.ui.activity

import android.view.View
import com.app.shop.R
import com.app.shop.base.BaseActivity
import com.app.shop.databinding.ActivityForgetPasswordBinding
import com.app.shop.ui.contract.ForgetPasswordContract
import com.app.shop.ui.presenter.ForgetPasswordPresent
import com.gyf.immersionbar.ktx.immersionBar

/**
 * @author chenshichun
 * 创建日期：2022/7/13
 * 描述：忘记密码
 */
class ForgetPasswordActivity : BaseActivity<ActivityForgetPasswordBinding, ForgetPasswordPresent>(),
    ForgetPasswordContract.View, View.OnClickListener {
    override fun getPresenter(): ForgetPasswordPresent {
        return ForgetPasswordPresent()
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
}