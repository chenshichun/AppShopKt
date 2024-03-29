package com.app.shop.ui.activity

import android.content.ClipData
import android.content.ClipboardManager
import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import com.app.shop.R
import com.app.shop.base.BaseActivity
import com.app.shop.bean.BaseNetModel
import com.app.shop.bean.type.SmsType
import com.app.shop.databinding.ActivityRegisterBinding
import com.app.shop.manager.Constants
import com.app.shop.req.RegisterReq
import com.app.shop.req.SmsSendReq
import com.app.shop.ui.contract.RegisterContract
import com.app.shop.ui.presenter.RegisterPresenter
import com.app.shop.util.IntentUtil
import com.app.shop.util.TimerUnit
import com.app.shop.util.ToastUtil
import com.gyf.immersionbar.ktx.immersionBar
import com.orhanobut.logger.Logger
import android.text.Editable

import android.text.TextWatcher


/**
 * @author chenshichun
 * 创建日期：2022/7/13
 * 描述：注册
 */
class RegisterActivity : BaseActivity<ActivityRegisterBinding, RegisterPresenter>(),
    RegisterContract.View, View.OnClickListener {

    private var timer: TimerUnit? = null

    override fun getPresenter(): RegisterPresenter {
        return RegisterPresenter()
    }

    override fun initView() {
        immersionBar {
            statusBarDarkFont(true)
        }
        binding.ivBack.setOnClickListener(this)
        binding.tvRegister.setOnClickListener(this)
        binding.tvGetSmsCode.setOnClickListener(this)
        binding.tvPrivacyPolicy.setOnClickListener(this)
        binding.tvUserAgreement.setOnClickListener(this)
        /*Logger.d(getClipboardContent())
        if (getClipboardContent()!!.isNotEmpty()) {
            binding.etInvCode.setText(getClipboardContent())
        }*/
        binding.etInvCode.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                if (binding.etInvCode.text.contains("易货君邀请码=")) {
                    binding.etInvCode.setText(binding.etInvCode.text.substring(7, binding.etInvCode.text.length))
                }
            }
        })
    }

    /**
     * 系统剪贴板-获取:
     */
    fun getClipboardContent(): String? {
        // 获取系统剪贴板
        val clipboard = this.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
        // 返回数据
        val clipData = clipboard.primaryClip
        if (clipData == null || clipData.itemCount <= 0) {
            return ""
        }
        val item: ClipData.Item? = clipData.getItemAt(0)
        return if (item == null || item.getText() == null) {
            ""
        } else item.getText().toString()
    }

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
            R.id.tv_get_sms_code -> {

                if (binding.etPhone.text.toString().isEmpty()) {
                    ToastUtil.showToast("请输入手机号码")
                    return
                }

                var smsSendReq = SmsSendReq()
                smsSendReq.phone = binding.etPhone.text.toString()
                smsSendReq.sms_type = SmsType.register.name
                mPresenter!!.smsCode(smsSendReq)
            }
            R.id.tv_register -> {
                if (!binding.checkbox.isChecked) {
                    ToastUtil.showToast("请阅读并同意《隐私政策》和《用户服务协议》")
                    return
                }
                if (binding.etPhone.text.toString().isEmpty()) {
                    ToastUtil.showToast("请输入手机号码")
                    return
                }
                if (binding.etPassword.text.toString().isEmpty()) {
                    ToastUtil.showToast("密码不能为空")
                    return
                }
                if (binding.etSmsCode.text.toString().isEmpty()) {
                    ToastUtil.showToast("验证码不能为空")
                    return
                }
                val registerReq = RegisterReq()
                registerReq.phone = binding.etPhone.text.toString()
                registerReq.password = binding.etPassword.text.toString()
                registerReq.sms_code = binding.etSmsCode.text.toString()
                registerReq.inv_code = binding.etInvCode.text.toString()
                mPresenter!!.smsRegister(registerReq)
            }
        }
    }

    override fun smsCode(mData: BaseNetModel<Any>) {
        ToastUtil.showToast(mData.msg)
        if (timer == null) {
            timer = TimerUnit(binding.tvGetSmsCode)
        }
        timer?.startTime()
    }

    override fun smsRegister(mData: BaseNetModel<Any>) {
        finish()
    }

    override fun showLoading() {
        showLoadingDialog()
    }

    override fun hideLoading() {
        closeLoadingDialog()
    }

}