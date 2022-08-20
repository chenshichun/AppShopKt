package com.app.shop.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.app.shop.R
import com.app.shop.base.BaseActivity
import com.app.shop.base.BaseConstant
import com.app.shop.bean.BaseNetModel
import com.app.shop.bean.type.SmsType
import com.app.shop.bean.UserDataBean
import com.app.shop.databinding.ActivityLoginBinding
import com.app.shop.manager.AccountManager
import com.app.shop.manager.Constants
import com.app.shop.req.SmsLoginReq
import com.app.shop.req.SmsSendReq
import com.app.shop.req.WxLoginReq
import com.app.shop.ui.contract.LoginContract
import com.app.shop.ui.presenter.LoginPresenter
import com.app.shop.util.AppUtil
import com.app.shop.util.IntentUtil
import com.app.shop.util.TimerUnit
import com.app.shop.util.ToastUtil
import com.app.shop.wxapi.WXEntryActivity
import com.gyf.immersionbar.ktx.immersionBar
import com.tencent.mm.opensdk.modelmsg.SendAuth
import com.tencent.mm.opensdk.openapi.IWXAPI
import com.tencent.mm.opensdk.openapi.WXAPIFactory


/**
 * @author chenshichun
 * 创建日期：2022/7/13
 * 描述：登录
 */
class LoginActivity : BaseActivity<ActivityLoginBinding, LoginPresenter>(), LoginContract.View,
    View.OnClickListener, WXEntryActivity.Back {

    private var api: IWXAPI? = null

    override fun getPresenter(): LoginPresenter {
        return LoginPresenter()
    }

    override fun initView() {
        immersionBar {
            statusBarDarkFont(true)
        }

        api = WXAPIFactory.createWXAPI(this, BaseConstant.WX_APP_ID, true)
        api!!.registerApp(BaseConstant.WX_APP_ID)

        WXEntryActivity.registAuthBack(this)

        binding.ivBack.setOnClickListener(this)
        binding.tvLogin.setOnClickListener(this)
        binding.tvWxLogin.setOnClickListener(this)
        binding.tvRegister.setOnClickListener(this)
        binding.tvPasswordLogin.setOnClickListener(this)
        binding.tvGetCode.setOnClickListener(this)
        binding.tvPrivacyPolicy.setOnClickListener(this)
        binding.tvUserAgreement.setOnClickListener(this)
    }

    override fun onDestroy() {
        api!!.unregisterApp()
        WXEntryActivity.unregistAuthBack()
        super.onDestroy()
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
            R.id.tv_wx_login -> {
                if (!binding.checkbox.isChecked) {
                    ToastUtil.showToast("请阅读并同意《隐私政策》和《用户服务协议》")
                    return
                }
                if (AppUtil.isWeixinAvilible(this)) {
                    //初始化登录请求对象
                    val req = SendAuth.Req()
                    req.scope = "snsapi_userinfo"
                    req.state = System.currentTimeMillis().toString()
                    api!!.sendReq(req)
                } else {
                    ToastUtil.showToast("请先安装微信")
                }
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

    /*
    * 微信登录成功
    * */
    override fun wechatLogin(mData: BaseNetModel<UserDataBean>) {
        ToastUtil.showToast(mData.msg!!)
        AccountManager.signIn(mData.data!!.user!!)
        AccountManager.signInToken(mData.data!!.user!!.token!!)
        startActivity(Intent(this@LoginActivity, MainActivity::class.java))
        finish()
    }

    /*
    * 绑定手机号
    * */
    override fun bindPhone() {
        startActivity(Intent(this@LoginActivity, BindActivity::class.java))
    }

    override fun showLoading() {
        showLoadingDialog()
    }

    override fun hideLoading() {
        closeLoadingDialog()
    }

    override fun onWxLoginFiled(errorCode: Int) {
        ToastUtil.showToast("微信登录失败，请换个方式登录")
    }

    override fun onWxLoginSuccess(code: String?, state: String?) {
        val wxLoginReq = WxLoginReq()
        wxLoginReq.wechat_id = code
        mPresenter!!.wechatLogin(wxLoginReq)
    }
}