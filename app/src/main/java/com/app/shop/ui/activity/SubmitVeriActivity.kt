package com.app.shop.ui.activity

import android.os.Handler
import android.os.Message
import android.text.TextUtils
import com.alipay.sdk.app.PayTask
import com.app.shop.R
import com.app.shop.base.BaseActivity
import com.app.shop.bean.*
import com.app.shop.databinding.ActivitySubmitVeriBinding
import com.app.shop.manager.Constants
import com.app.shop.req.SubmitVeriReq
import com.app.shop.ui.contract.SubmitVeriContract
import com.app.shop.ui.presenter.SubmitVeriPresenter
import com.app.shop.util.JsonUtils
import com.app.shop.util.ToastUtil
import com.gyf.immersionbar.ktx.immersionBar
import com.orhanobut.logger.Logger

/**
 * @author chenshichun
 * 创建日期：2022/10/11
 * 描述：订单核销
 */
class SubmitVeriActivity : BaseActivity<ActivitySubmitVeriBinding, SubmitVeriPresenter>(),
    SubmitVeriContract.View {

    private var result: String = ""
    val SDK_PAY_FLAG = 1

    override fun getPresenter(): SubmitVeriPresenter {
        return SubmitVeriPresenter()
    }

    override fun initView() {
        immersionBar {
            statusBarColor(R.color.white)
            statusBarDarkFont(true)
        }

        binding.viewHead.tvTitle.text = "订单核销"
        binding.viewHead.ivBack.setOnClickListener {
            finish()
        }
        result = intent.getStringExtra(Constants.CODE_QR).toString()
        val veriBean: VeriBean = JsonUtils.jsonToBean(result, VeriBean::class.java)
        Logger.d(veriBean)
        binding.tvConfirm.setOnClickListener {
            val submitVeriReq =
                SubmitVeriReq(veriBean.order_number!!, binding.etPoint.text.toString(), veriBean.user_id!!)
            mPresenter!!.submitVeri(submitVeriReq)
        }
    }

    override fun submitVeri(mData: BaseNetModel<AliPayDataBean>) {
        toZFB(mData.data!!.ali_pay_data)
    }

    private fun toZFB(info: String) {
        val payRunnable = Runnable {
            val alipay = PayTask(this@SubmitVeriActivity)
            val result = alipay.payV2(info, true)
            val msg = Message()
            msg.what = SDK_PAY_FLAG
            msg.obj = result
            mHandler.sendMessage(msg)
        }
        // 必须异步调用
        val payThread = Thread(payRunnable)
        payThread.start()
    }

    private val mHandler: Handler = object : Handler() {
        override fun handleMessage(msg: Message) {
            when (msg.what) {
                SDK_PAY_FLAG -> {
                    val payResult = PayResult(msg.obj as Map<String?, String?>)

                    /**
                     * 对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    val resultInfo: String = payResult.getResult() // 同步返回需要验证的信息
                    val resultStatus: String = payResult.getResultStatus()
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        finish()
                        ToastUtil.showToast("核销成功")
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        ToastUtil.showToast("退出支付")
                    }
                }
            }
        }
    }

    override fun showLoading() {
        showLoadingDialog()
    }

    override fun hideLoading() {
        closeLoadingDialog()
    }
}