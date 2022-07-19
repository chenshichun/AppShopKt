package com.app.shop.ui.activity

import android.content.Intent
import android.view.View
import com.app.shop.R
import com.app.shop.base.BaseActivity
import com.app.shop.bean.BaseNetModel
import com.app.shop.bean.UserDataBean
import com.app.shop.databinding.ActivityLoginBinding
import com.app.shop.manager.AccountManager
import com.app.shop.req.SmsReq
import com.app.shop.ui.contract.LoginContract
import com.app.shop.ui.presenter.LoginPresent
import com.app.shop.util.TimerUnit
import com.app.shop.util.ToastUtil
import com.gyf.immersionbar.ktx.immersionBar
import com.orhanobut.logger.Logger


/**
 * @author chenshichun
 * 创建日期：2022/7/13
 * 描述：登录
 */
class LoginActivity : BaseActivity<ActivityLoginBinding, LoginPresent>(), LoginContract.View,
    View.OnClickListener {
    override fun getPresenter(): LoginPresent {
        return LoginPresent()
    }

    override fun initView() {
        immersionBar {
            statusBarColor(R.color.white)
            statusBarDarkFont(true)
        }
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
            R.id.tv_login -> {
                if (!binding.checkbox.isChecked) {
                    ToastUtil.showToast("请阅读并同意《隐私政策》和《用户服务协议》")
                    return
                }
                if (binding.etPhone.text.isEmpty()) {
                    ToastUtil.showToast("请输入电话号码")
                    return
                }
                if (binding.etCode.text.isEmpty()) {
                    ToastUtil.showToast("请输入验证码")
                    return
                }

                val smsReq = SmsReq()
                smsReq.phone = binding.etPhone.text.toString()
                smsReq.verify_code = binding.etCode.text.toString()
                mPresenter!!.smsLogin(smsReq)
            }
        }
    }

    override fun smsLogin(mData: BaseNetModel<UserDataBean>) {
        Logger.d(mData.data!!.user!!.token)
        AccountManager.signIn(mData.data!!.user!!)
        AccountManager.signInToken(mData.data!!.user!!.token!!)
        Logger.d(AccountManager.isLogin())
        Logger.d(AccountManager.getToken())
        startActivity(Intent(this@LoginActivity, MainActivity::class.java))
        finish()
    }
}