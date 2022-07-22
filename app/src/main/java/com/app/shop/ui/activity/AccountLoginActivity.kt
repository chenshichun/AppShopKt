package com.app.shop.ui.activity

import android.content.Intent
import android.view.View
import com.app.shop.R
import com.app.shop.base.BaseActivity
import com.app.shop.bean.BaseNetModel
import com.app.shop.bean.UserDataBean
import com.app.shop.databinding.ActivityAccountLoginBinding
import com.app.shop.manager.AccountManager
import com.app.shop.req.PwdLoginReq
import com.app.shop.ui.contract.AccountLoginContract
import com.app.shop.ui.presenter.AccountLoginPresenter
import com.app.shop.util.ToastUtil
import com.gyf.immersionbar.ktx.immersionBar
import com.orhanobut.logger.Logger

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
        immersionBar {
            statusBarColor(R.color.white)
            statusBarDarkFont(true)
        }
        binding.ivBack.setOnClickListener(this)
        binding.tvRegister.setOnClickListener(this)
        binding.tvLogin.setOnClickListener(this)
        binding.tvCodeLogin.setOnClickListener(this)
        binding.tvForgetPassword.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when (view!!.id) {
            R.id.iv_back -> finish()
            R.id.tv_code_login -> finish()
            R.id.tv_login -> {
                if (!binding.checkbox.isChecked) {
                    ToastUtil.showToast("请阅读并同意《隐私政策》和《用户服务协议》")
                    return
                }
                if (binding.etPhone.text.isEmpty()) {
                    ToastUtil.showToast("请输入电话号码")
                    return
                }
                if (binding.etPassword.text!!.isEmpty()) {
                    ToastUtil.showToast("请输入密码")
                    return
                }

                val pwdLoginReq = PwdLoginReq()
                pwdLoginReq.phone = binding.etPhone.text.toString()
                pwdLoginReq.password = binding.etPassword.text.toString()
                mPresenter!!.pwdLogin(pwdLoginReq)
            }
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

    override fun pwdLogin(mData: BaseNetModel<UserDataBean>) {
        AccountManager.signIn(mData.data!!.user!!)
        AccountManager.signInToken(mData.data!!.user!!.token!!)
        Logger.d(AccountManager.isLogin())
        Logger.d(AccountManager.getToken())
        startActivity(Intent(this@AccountLoginActivity, MainActivity::class.java))
        finish()
    }

    override fun showLoading() {
        showLoadingDialog()
    }

    override fun hideLoading() {
        closeLoadingDialog()
    }
}