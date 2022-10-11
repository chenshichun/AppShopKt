package com.app.shop.ui.activity

import android.os.Handler
import android.os.Message
import android.text.TextUtils
import androidx.recyclerview.widget.LinearLayoutManager
import com.alipay.sdk.app.PayTask
import com.app.shop.R
import com.app.shop.adapter.OrderCartAdapter
import com.app.shop.base.BaseActivity
import com.app.shop.bean.*
import com.app.shop.databinding.ActivityPayCartOrderBinding
import com.app.shop.req.OrderIdReq
import com.app.shop.ui.contract.PayCartOrderContract
import com.app.shop.ui.presenter.PayCartOrderPresenter
import com.app.shop.util.IntentUtil
import com.app.shop.util.ToastUtil
import com.gyf.immersionbar.ktx.immersionBar

/**
 * @author chenshichun
 * 创建日期：2022/10/11
 * 描述：
 */
class PayCartOrderActivity : BaseActivity<ActivityPayCartOrderBinding, PayCartOrderPresenter>(),
    PayCartOrderContract.View {
    lateinit var cartOrderBean: CartOrderBean
    lateinit var orderCartAdapter: OrderCartAdapter
    val SDK_PAY_FLAG = 1
    var currentPos = 0;
    private val cartOrderBeanList = mutableListOf<CartOrderDetailBean>()
    override fun getPresenter(): PayCartOrderPresenter {
        return PayCartOrderPresenter()
    }

    override fun initView() {
        immersionBar {
            statusBarColor(R.color.white)
            statusBarDarkFont(true)
        }
        binding.viewHead.tvTitle.text = "订单支付"
        binding.viewHead.ivBack.setOnClickListener {
            finish()
        }
        cartOrderBean = IntentUtil.getParcelableExtra<CartOrderBean>(this)!!
        cartOrderBeanList.clear()
        cartOrderBeanList.addAll(cartOrderBean.detail!!)
        orderCartAdapter = OrderCartAdapter(this, cartOrderBeanList)
        binding.mRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.mRecyclerView.adapter = orderCartAdapter

        orderCartAdapter.setOnItemClickListener(object : OrderCartAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {

            }

            override fun onPayClick(position: Int) {
                currentPos = position
                val orderIdReq = OrderIdReq(cartOrderBean.detail!![position].order_id!!)
                mPresenter!!.aliPay(orderIdReq)
            }
        })
    }

    override fun aliPay(mData: BaseNetModel<AliPayDataBean>) {
        toZFB(mData.data!!.ali_pay_data)
    }

    private fun toZFB(info: String) {
        val payRunnable = Runnable {
            val alipay = PayTask(this@PayCartOrderActivity)
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
                        if (cartOrderBeanList.size > 1) {
                            cartOrderBeanList.removeAt(currentPos)
                            orderCartAdapter.notifyDataSetChanged()
                        } else {
                            finish()
                        }
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