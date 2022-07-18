package com.app.shop.ui.activity

import android.content.Intent
import android.view.View
import com.app.shop.R
import com.app.shop.base.BaseActivity
import com.app.shop.databinding.ActivityLoginBinding
import com.app.shop.ui.presenter.LoginPresent
import com.app.shop.util.TimerUnit


/**
 * @author chenshichun
 * 创建日期：2022/7/13
 * 描述：登录
 */
class LoginActivity : BaseActivity<ActivityLoginBinding, LoginPresent>(), View.OnClickListener {
    override fun getPresenter(): LoginPresent {
        return LoginPresent()
    }

    override fun initView() {
        binding.ivBack.setOnClickListener(this)
        binding.tvLogin.setOnClickListener(this)
        binding.tvWxLogin.setOnClickListener(this)
        binding.tvRegister.setOnClickListener(this)
        binding.tvPasswordLogin.setOnClickListener(this)
        binding.tvGetCode.setOnClickListener(this)
    }

    private var timer: TimerUnit? = null
    override fun onClick(view: View?) {
        when (view!!.id) {
            R.id.iv_back -> finish()
            R.id.tv_password_login -> startActivity(
                Intent(
                    this@LoginActivity,
                    AccountLoginActivity::class.java
                )
            )
            R.id.tv_register -> startActivity(
                Intent(
                    this@LoginActivity,
                    RegisterActivity::class.java
                )
            )
            R.id.tv_get_code -> {// 获取验证码
                if (timer == null) {
                    timer = TimerUnit(binding.tvGetCode)
                }
                timer?.startTime()
            }
        }
    }

}