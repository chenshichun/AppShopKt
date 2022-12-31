package com.app.shop.ui.activity

import android.view.View
import com.app.shop.R
import com.app.shop.base.BaseActivity
import com.app.shop.bean.BaseNetModel
import com.app.shop.bean.type.SmsType
import com.app.shop.databinding.ActivityChangeBindPhoneBinding
import com.app.shop.req.SmsSendReq
import com.app.shop.ui.contract.ChangeBindPhoneContract
import com.app.shop.ui.presenter.ChangeBindPhonePresenter
import com.app.shop.util.TimerUnit
import com.app.shop.util.ToastUtil
import com.gyf.immersionbar.ktx.immersionBar

/**
 * @author chenshichun
 * 创建日期：2022/7/25
 * 描述：换绑手机号
 */
class ChangeBindPhoneActivity :
    BaseActivity<ActivityChangeBindPhoneBinding, ChangeBindPhonePresenter>(),
    ChangeBindPhoneContract.View, View.OnClickListener {

    private var timer: TimerUnit? = null

    override fun getPresenter(): ChangeBindPhonePresenter {
        return ChangeBindPhonePresenter()
    }

    override fun initView() {
        immersionBar {
            statusBarColor(R.color.white)
            statusBarDarkFont(true)
        }

        binding.viewHead.tvTitle.text = "换绑手机号"
        binding.viewHead.ivBack.setOnClickListener {
            finish()
        }

        binding.btConfirm.setOnClickListener(this)
        binding.tvGetCode.setOnClickListener(this)
    }

    override fun smsCode(mData: BaseNetModel<Any>) {
        ToastUtil.showToast(mData.msg)
        if (timer == null) {
            timer = TimerUnit(binding.tvGetCode)
        }
        timer?.startTime()
    }

    override fun showLoading() {
        showLoadingDialog()
    }

    override fun hideLoading() {
        closeLoadingDialog()
    }

    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.tv_get_code -> {
                if (binding.etPhone.text.isEmpty()) {
                    ToastUtil.showToast("手机号不能为空")
                    return
                }
                val smsSendReq = SmsSendReq()
                smsSendReq.phone = binding.etPhone.text.toString()
                smsSendReq.sms_type = SmsType.register.name
                mPresenter!!.smsCode(smsSendReq)
            }
            R.id.bt_confirm -> {
                if (binding.etPhone.text.isEmpty()) {
                    ToastUtil.showToast("请输入手机号")
                    return
                }
                if (binding.etCode.text.isEmpty()) {
                    ToastUtil.showToast("请输入验证码")
                    return
                }
            }
        }
    }

}