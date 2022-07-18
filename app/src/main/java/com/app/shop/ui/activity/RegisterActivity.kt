package com.app.shop.ui.activity

import android.view.View
import com.app.shop.R
import com.app.shop.base.BaseActivity
import com.app.shop.databinding.ActivityRegisterBinding
import com.app.shop.ui.contract.RegisterContract
import com.app.shop.ui.presenter.RegisterPresenter

/**
 * @author chenshichun
 * 创建日期：2022/7/13
 * 描述：注册
 */
class RegisterActivity : BaseActivity<ActivityRegisterBinding, RegisterPresenter>(),
    RegisterContract.View, View.OnClickListener {
    override fun getPresenter(): RegisterPresenter {
        return RegisterPresenter()
    }

    override fun initView() {
        binding.ivBack.setOnClickListener(this)
        binding.tvRegister.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when (view!!.id) {
            R.id.iv_back -> finish()
            R.id.tv_register -> {

            }
        }
    }
}