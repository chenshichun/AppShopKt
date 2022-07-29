package com.app.shop.ui.activity

import android.view.View
import com.app.shop.R
import com.app.shop.base.BaseActivity
import com.app.shop.bean.type.SmsType
import com.app.shop.databinding.ActivityBindBinding
import com.app.shop.req.BindWechatReq
import com.app.shop.req.SmsSendReq
import com.app.shop.ui.contract.BindContract
import com.app.shop.ui.presenter.BindPresenter
import com.app.shop.util.TimerUnit
import com.app.shop.util.ToastUtil
import com.gyf.immersionbar.ktx.immersionBar

/**
 * @author chenshichun
 * 创建日期：2022/7/13
 * 描述：绑定手机号
 */
class BindActivity : BaseActivity<ActivityBindBinding, BindPresenter>(),
    BindContract.Presenter, View.OnClickListener {

    override fun getPresenter(): BindPresenter {
        return BindPresenter()
    }

    override fun initView() {
        immersionBar {
            statusBarColor(R.color.white)
            statusBarDarkFont(true)
        }
        binding.ivBack.setOnClickListener(this)
        binding.tvGetSmsCode.setOnClickListener(this)
        binding.tvBind.setOnClickListener(this)
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
                smsSendReq.sms_type = SmsType.register.name
                mPresenter!!.smsCode(smsSendReq)
            }
            R.id.tv_bind -> {
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
                val bindWechatReq = BindWechatReq(
                    "1234",
                    binding.etPassword.text.toString(),
                    binding.etPhone.text.toString(),
                    binding.etCode.text.toString(),
                    ""
                )
                mPresenter!!.bindWechat(bindWechatReq)
            }
        }
    }

    override fun showLoading() {
        showLoadingDialog()
    }

    override fun hideLoading() {
        closeLoadingDialog()
    }

    private var timer: TimerUnit? = null
    override fun smsCode(smsSendReq: SmsSendReq) {
        if (timer == null) {
            timer = TimerUnit(binding.tvGetSmsCode)
        }
        timer?.startTime()
    }

    override fun bindWechat(bindWechatReq: BindWechatReq) {

    }
}