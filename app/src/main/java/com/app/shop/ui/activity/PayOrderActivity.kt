package com.app.shop.ui.activity

import android.os.Handler
import android.os.Message
import android.text.TextUtils
import com.alipay.sdk.app.PayTask
import com.app.shop.R
import com.app.shop.base.BaseActivity
import com.app.shop.bean.*
import com.app.shop.databinding.ActivityPayOrderBinding
import com.app.shop.req.BalancePayReq
import com.app.shop.req.ZFBPayReq
import com.app.shop.ui.contract.PayOrderContract
import com.app.shop.ui.presenter.PayOrderPresenter
import com.app.shop.util.IntentUtil
import com.app.shop.util.ToastUtil
import com.app.shop.view.dialog.PasswordDialog
import com.bumptech.glide.Glide
import com.gyf.immersionbar.ktx.immersionBar

/**
 * @author chenshichun
 * 创建日期：2022/8/1
 * 描述：支付订单
 */
class PayOrderActivity : BaseActivity<ActivityPayOrderBinding, PayOrderPresenter>(),
    PayOrderContract.View {
    private var orderId: String = ""
    private val SDK_PAY_FLAG = 1

    override fun getPresenter(): PayOrderPresenter {
        return PayOrderPresenter()
    }

    override fun initView() {
        immersionBar {
            statusBarColor(R.color.white)
            statusBarDarkFont(true)
        }
        binding.viewHead.tvTitle.text = "确认订单"
        binding.viewHead.ivBack.setOnClickListener {
            finish()
        }
        val orderInfoBean = IntentUtil.getParcelableExtra<OrderInfoBean>(this)!!
        Glide.with(this).load(orderInfoBean.ivGoods).error(R.drawable.icon_default_pic)
            .placeholder(R.drawable.icon_default_pic).into(binding.ivGoods)
        binding.tvPrice.text = String.format(getString(R.string.price), orderInfoBean.goodsPrice)
        binding.tvName.text = orderInfoBean.goodsName
        orderId = orderInfoBean.orderId.toString()

        binding.btConfirm.setOnClickListener {
            var point_type = ""
            if (binding.rb1.isChecked) {
                point_type = "reward"
            } else if (binding.rb2.isChecked) {
                point_type = "barter"
            } else if (binding.rb3.isChecked) {
                point_type = "expend"
            }
            if (binding.rb4.isChecked) {
                val ZFBPayReq = ZFBPayReq(orderId, point_type)
                mPresenter!!.aliPay(ZFBPayReq)
            } else {
                val passwordDialog =
                    PasswordDialog(this@PayOrderActivity, R.style.CustomDialog)
                passwordDialog.setOnClickListener(object : PasswordDialog.OnClickListener {
                    override fun cancel() {
                        passwordDialog.dismiss()
                    }
                    override fun pay(psw: String) {
                        val balancePayReq = BalancePayReq(orderId, point_type,psw)
                        mPresenter!!.payBalance(balancePayReq)
                        passwordDialog.dismiss()
                    }
                })
                passwordDialog.show()
            }
        }

        binding.rb4.setOnCheckedChangeListener { compoundButton, b ->
            if (b) {
                binding.rb5.isChecked = false
            }
        }
        binding.rb5.setOnCheckedChangeListener { compoundButton, b ->
            if (b) {
                binding.rb4.isChecked = false
            }
        }
    }

    override fun onResume() {
        mPresenter!!.pointInfo()
        super.onResume()
    }

    override fun aliPay(mData: BaseNetModel<AliPayDataBean>) {
        toZFB(mData.data!!.ali_pay_data)
    }

    override fun pointInfo(mData: BaseNetModel<PointInfoBean>) {
        binding.rb1.text = String.format(getString(R.string.qdjf), mData.data!!.point.reward)
        binding.rb2.text = String.format(getString(R.string.yhjf), mData.data!!.point.barter)
        binding.rb3.text = String.format(getString(R.string.xfjf), mData.data!!.point.expend)
        binding.tvYe.text = String.format(getString(R.string.ye), mData.data!!.point.balance)
    }

    override fun payBalance(mData: BaseNetModel<Any>) {
        finish()
    }

    private fun toZFB(info: String) {
        val payRunnable = Runnable {
            val alipay = PayTask(this@PayOrderActivity)
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
                        ToastUtil.showToast("支付成功")
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