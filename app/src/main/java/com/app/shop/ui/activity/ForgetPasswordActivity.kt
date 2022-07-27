package com.app.shop.ui.activity

import android.view.View
import com.app.shop.R
import com.app.shop.base.BaseActivity
import com.app.shop.bean.BaseNetModel
import com.app.shop.bean.SmsType
import com.app.shop.databinding.ActivityForgetPasswordBinding
import com.app.shop.req.SmsSendReq
import com.app.shop.ui.contract.ForgetPasswordContract
import com.app.shop.ui.presenter.ForgetPasswordPresent
import com.app.shop.util.TimerUnit
import com.app.shop.util.ToastUtil
import com.gyf.immersionbar.ktx.immersionBar

/**
 * @author chenshichun
 * 创建日期：2022/7/13
 * 描述：忘记密码
 */
class ForgetPasswordActivity : BaseActivity<ActivityForgetPasswordBinding, ForgetPasswordPresent>(),
    ForgetPasswordContract.View, View.OnClickListener {

    private var timer: TimerUnit? = null

    override fun getPresenter(): ForgetPasswordPresent {
        return ForgetPasswordPresent()
    }

    override fun initView() {
        immersionBar {
            statusBarColor(R.color.white)
            statusBarDarkFont(true)
        }
        binding.ivBack.setOnClickListener(this)

        binding.tvGetSmsCode.setOnClickListener(this)
        binding.tvConfirm.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when (view!!.id) {
            R.id.iv_back -> finish()
            R.id.tv_get_sms_code -> {
                if (binding.etPhone.text.toString().isEmpty()) {
                    ToastUtil.showToast("请输入手机号码")
                    return
                }
                val smsSendReq = SmsSendReq()
                smsSendReq.phone = binding.etPhone.text.toString()
                smsSendReq.sms_type = SmsType.resetpwd.name
                mPresenter!!.smsCode(smsSendReq)
            }
            R.id.tv_confirm -> {
                if (binding.etPhone.text.toString().isEmpty()) {
                    ToastUtil.showToast("请输入手机号码")
                    return
                }
                if (binding.etPassword.text.toString().isEmpty()) {
                    ToastUtil.showToast("请输入密码")
                    return
                }
                if (binding.etCode.text.toString().isEmpty()) {
                    ToastUtil.showToast("请输入验证码")
                    return
                }
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

    override fun showLoading() {
        showLoadingDialog()
    }

    override fun hideLoading() {
        closeLoadingDialog()
    }
}