package com.app.shop.ui.activity

import android.view.View
import com.app.shop.R
import com.app.shop.base.BaseActivity
import com.app.shop.bean.BaseNetModel
import com.app.shop.bean.SmsType
import com.app.shop.databinding.ActivityRegisterBinding
import com.app.shop.req.RegisterReq
import com.app.shop.req.SmsSendReq
import com.app.shop.ui.contract.RegisterContract
import com.app.shop.ui.presenter.RegisterPresenter
import com.app.shop.util.TimerUnit
import com.app.shop.util.ToastUtil
import com.gyf.immersionbar.ktx.immersionBar

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
            statusBarColor(R.color.white)
            statusBarDarkFont(true)
        }
        binding.ivBack.setOnClickListener(this)
        binding.tvRegister.setOnClickListener(this)
        binding.tvGetSmsCode.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when (view!!.id) {
            R.id.iv_back -> finish()
            R.id.tv_get_sms_code -> {

                if (binding.etPhone.text.toString().isEmpty()) {
                    ToastUtil.showToast("请输入手机号码")
                    return
                }

                var smsSendReq = SmsSendReq()
                smsSendReq.phone = binding.etPhone.text.toString()
                smsSendReq.sms_type= SmsType.register.name
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
        if (timer == null) {
            timer = TimerUnit(binding.tvGetSmsCode)
        }
        timer?.startTime()
    }

    override fun smsRegister(mData: BaseNetModel<Any>) {

    }
}