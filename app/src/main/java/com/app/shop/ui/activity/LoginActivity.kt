package com.app.shop.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.app.shop.R
import com.app.shop.base.BaseActivity
import com.app.shop.bean.BaseNetModel
import com.app.shop.bean.UserDataBean
import com.app.shop.bean.type.SmsType
import com.app.shop.databinding.ActivityLoginBinding
import com.app.shop.manager.AccountManager
import com.app.shop.manager.Constants
import com.app.shop.req.SmsLoginReq
import com.app.shop.req.SmsSendReq
import com.app.shop.ui.contract.LoginContract
import com.app.shop.ui.presenter.LoginPresenter
import com.app.shop.util.IntentUtil
import com.app.shop.util.TimerUnit
import com.app.shop.util.ToastUtil
import com.gyf.immersionbar.ktx.immersionBar


/**
 * @author chenshichun
 * 创建日期：2022/7/13
 * 描述：登录
 */
class LoginActivity : BaseActivity<ActivityLoginBinding, LoginPresenter>(), LoginContract.View,
    View.OnClickListener {

    override fun getPresenter(): LoginPresenter {
        return LoginPresenter()
    }

    override fun initView() {
        immersionBar {
            statusBarDarkFont(true)
        }

        binding.ivBack.setOnClickListener(this)
        binding.tvLogin.setOnClickListener(this)
        binding.tvRegister.setOnClickListener(this)
        binding.tvPasswordLogin.setOnClickListener(this)
        binding.tvGetCode.setOnClickListener(this)
        binding.tvPrivacyPolicy.setOnClickListener(this)
        binding.tvUserAgreement.setOnClickListener(this)
    }

    private var timer: TimerUnit? = null
    override fun onClick(view: View?) {
        val bundle = Bundle()
        when (view!!.id) {
            R.id.iv_back -> finish()
            R.id.tv_privacy_policy -> {
                bundle.putString(Constants.WEB_TITLE, "隐私政策")
                bundle.putString(Constants.WEB_URL, Constants.PRIVACT_POLICY_URL)
                IntentUtil.get()!!.goActivity(this, WebViewActivity::class.java, bundle)
            }
            R.id.tv_user_agreement -> {
                bundle.putString(Constants.WEB_TITLE, "用户协议")
                bundle.putString(Constants.WEB_URL, Constants.USET_AGREEMENT_URL)
                IntentUtil.get()!!.goActivity(this, WebViewActivity::class.java, bundle)
            }
            R.id.tv_password_login -> finish()

            R.id.tv_register -> startActivity(
                Intent(
                    this@LoginActivity,
                    RegisterActivity::class.java
                )
            )
            R.id.tv_get_code -> {// 获取验证码
                if (binding.etPhone.text.toString().isEmpty()) {
                    ToastUtil.showToast("请输入手机号码")
                    return
                }
                val smsSendReq = SmsSendReq()
                smsSendReq.phone = binding.etPhone.text.toString()
                smsSendReq.sms_type = SmsType.login.name
                mPresenter!!.smsCode(smsSendReq)
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

                val smsLoginReq = SmsLoginReq()
                smsLoginReq.phone = binding.etPhone.text.toString()
                smsLoginReq.verify_code = binding.etCode.text.toString()
                mPresenter!!.smsLogin(smsLoginReq)
            }

        }
    }

    override fun smsCode(mData: BaseNetModel<Any>) {
        ToastUtil.showToast(mData.msg)
        if (timer == null) {
            timer = TimerUnit(binding.tvGetCode)
        }
        timer?.startTime()
    }


    /*
    * 短信登录成功
    * */
    override fun smsLogin(mData: BaseNetModel<UserDataBean>) {
        ToastUtil.showToast(mData.msg!!)
        AccountManager.signIn(mData.data!!.user!!)
        AccountManager.signInToken(mData.data!!.user!!.token!!)
        startActivity(Intent(this@LoginActivity, MainActivity::class.java))
        finish()
    }


    override fun showLoading() {
        showLoadingDialog()
    }

    override fun hideLoading() {
        closeLoadingDialog()
    }

}