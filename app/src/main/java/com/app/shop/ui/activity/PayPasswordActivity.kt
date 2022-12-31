package com.app.shop.ui.activity

import com.app.shop.R
import com.app.shop.base.BaseActivity
import com.app.shop.bean.BaseNetModel
import com.app.shop.bean.type.SmsType
import com.app.shop.databinding.ActivityPayPasswordBinding
import com.app.shop.req.ModifyPasReq
import com.app.shop.req.SmsSendReq
import com.app.shop.ui.contract.PayPasswordContract
import com.app.shop.ui.presenter.PayPasswordPresenter
import com.app.shop.util.TimerUnit
import com.app.shop.util.ToastUtil
import com.gyf.immersionbar.ktx.immersionBar

/**
 * @author chenshichun
 * 创建日期：2022/7/25
 * 描述：支付密码
 */
class PayPasswordActivity : BaseActivity<ActivityPayPasswordBinding, PayPasswordPresenter>(),
    PayPasswordContract.View {

    override fun getPresenter(): PayPasswordPresenter {
        return PayPasswordPresenter()
    }

    override fun initView() {
        immersionBar {
            statusBarColor(R.color.white)
            statusBarDarkFont(true)
        }

        binding.viewHead.tvTitle.text = "支付密码"
        binding.viewHead.ivBack.setOnClickListener {
            finish()
        }

        binding.tvGetSmsCode.setOnClickListener {
            if (binding.etPhone.text.toString().isEmpty()) {
                ToastUtil.showToast("请输入手机号码")
                return@setOnClickListener
            }
            val smsSendReq = SmsSendReq()
            smsSendReq.phone = binding.etPhone.text.toString()
            smsSendReq.sms_type = SmsType.paypwd.name
            mPresenter!!.smsCode(smsSendReq)
        }

        binding.btConfirm.setOnClickListener {
            if (binding.etPassword.text.isEmpty()) {
                ToastUtil.showToast("请输入新密码")
                return@setOnClickListener
            }
            val modifyPasReq =
                ModifyPasReq(binding.etPassword.text.toString(), binding.etCode.text.toString())
            mPresenter!!.setPwdPay(modifyPasReq)
        }
    }

    private var timer: TimerUnit? = null
    override fun smsCode(mData: BaseNetModel<Any>) {
        if (timer == null) {
            timer = TimerUnit(binding.tvGetSmsCode)
        }
        timer?.startTime()
    }

    override fun setPwdPay(mData: BaseNetModel<Any>) {
        finish()
    }

    override fun showLoading() {
        showLoadingDialog()
    }

    override fun hideLoading() {
        closeLoadingDialog()
    }
}