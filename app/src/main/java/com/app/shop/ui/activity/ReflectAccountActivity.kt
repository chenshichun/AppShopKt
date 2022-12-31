package com.app.shop.ui.activity

import android.text.Editable
import android.view.View
import com.app.shop.R
import com.app.shop.base.BaseActivity
import com.app.shop.bean.BaseNetModel
import com.app.shop.bean.type.SmsType
import com.app.shop.databinding.ActivityReflectAccountBinding
import com.app.shop.manager.AccountManager
import com.app.shop.req.SmsSendReq
import com.app.shop.req.WalletSetReq
import com.app.shop.ui.contract.ReflectAccountContract
import com.app.shop.ui.presenter.ReflectAccountPresenter
import com.app.shop.util.TimerUnit
import com.app.shop.util.ToastUtil
import com.gyf.immersionbar.ktx.immersionBar

/**
 * @author chenshichun
 * 创建日期：2022/7/13
 * 描述：提现账号
 */
class ReflectAccountActivity :
    BaseActivity<ActivityReflectAccountBinding, ReflectAccountPresenter>(),
    ReflectAccountContract.View, View.OnClickListener {

    private var timer: TimerUnit? = null

    override fun getPresenter(): ReflectAccountPresenter {
        return ReflectAccountPresenter()
    }

    override fun initView() {
        immersionBar {
            statusBarColor(R.color.white)
            statusBarDarkFont(true)
        }

        binding.viewHead.tvTitle.text = "提现账号"
        binding.viewHead.ivBack.setOnClickListener {
            finish()
        }

        binding.etAccount.text =
            Editable.Factory.getInstance().newEditable(AccountManager.getAccountInfo()!!.phone)

        binding.tvGetCode.setOnClickListener(this)


        binding.btConfirm.setOnClickListener(this)
    }

    override fun smsCode(mData: BaseNetModel<Any>) {
        ToastUtil.showToast(mData.msg)
        if (timer == null) {
            timer = TimerUnit(binding.tvGetCode)
        }
        timer?.startTime()
    }

    override fun walletSet(mData: BaseNetModel<Any>) {
        finish()
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
                if (binding.etAccount.text.isEmpty()) {
                    ToastUtil.showToast("请输入支付宝账号")
                    return
                }
                val smsSendReq = SmsSendReq()
                smsSendReq.phone = binding.etAccount.text.toString()
                smsSendReq.sms_type = SmsType.setwallet.name
                mPresenter!!.smsCode(smsSendReq)
            }
            R.id.bt_confirm -> {
                if (binding.etName.text.isEmpty()) {
                    ToastUtil.showToast("请输入支付宝收款人姓名")
                    return
                }
                if (binding.etAccount.text.isEmpty()) {
                    ToastUtil.showToast("请输入支付宝账号")
                    return
                }
                if (binding.etPassword.text.isEmpty()) {
                    ToastUtil.showToast("请输入支付密码")
                    return
                }
                if (binding.etCode.text.isEmpty()) {
                    ToastUtil.showToast("请输入短信验证码")
                    return
                }
                val walletSetReq = WalletSetReq()
                walletSetReq.payee = binding.etName.text.toString()
                walletSetReq.ali_account = binding.etAccount.text.toString()
                walletSetReq.pay_pwd = binding.etPassword.text.toString()
                walletSetReq.sms_code = binding.etCode.text.toString()
                mPresenter!!.walletSet(walletSetReq)
            }
        }
    }
}