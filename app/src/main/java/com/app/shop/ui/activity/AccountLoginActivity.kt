package com.app.shop.ui.activity

import android.content.Intent
import android.view.View
import com.app.shop.R
import com.app.shop.base.BaseActivity
import com.app.shop.databinding.ActivityAccountLoginBinding
import com.app.shop.ui.contract.AccountLoginContract
import com.app.shop.ui.presenter.AccountLoginPresenter

/**
 * @author chenshichun
 * 创建日期：2022/7/13
 * 描述：账号密码登录
 */
class AccountLoginActivity : BaseActivity<ActivityAccountLoginBinding, AccountLoginPresenter>(),
    AccountLoginContract.View, View.OnClickListener {

    override fun getPresenter(): AccountLoginPresenter {
        return AccountLoginPresenter()
    }

    override fun initView() {
        binding.ivBack.setOnClickListener(this)
        binding.tvRegister.setOnClickListener(this)
        binding.tvCodeLogin.setOnClickListener(this)
        binding.tvForgetPassword.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when (view!!.id) {
            R.id.iv_back -> finish()
            R.id.tv_code_login -> finish()
            R.id.tv_register -> startActivity(
                Intent(
                    this@AccountLoginActivity,
                    RegisterActivity::class.java
                )
            )
            R.id.tv_forget_password -> startActivity(
                Intent(
                    this@AccountLoginActivity,
                    ForgetPasswordActivity::class.java
                )
            )
        }
    }
}