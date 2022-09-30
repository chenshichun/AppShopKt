package com.app.shop.ui.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.shop.R
import com.app.shop.adapter.OrderDetailGoodsAdapter
import com.app.shop.base.BaseActivity
import com.app.shop.bean.BaseNetModel
import com.app.shop.bean.OrderDetailBean
import com.app.shop.bean.OrderItem
import com.app.shop.databinding.ActivityOrderDetailBinding
import com.app.shop.manager.Constants
import com.app.shop.req.OrderIdReq
import com.app.shop.ui.contract.OrderDetailContract
import com.app.shop.ui.presenter.OrderDetailPresenter
import com.app.shop.util.IntentUtil
import com.gyf.immersionbar.ktx.immersionBar

/*
* 订单详情
* */
class OrderDetailActivity : BaseActivity<ActivityOrderDetailBinding, OrderDetailPresenter>(),
    OrderDetailContract.View {
    private var orderId: String = ""
    private lateinit var orderDetailGoodsAdapter: OrderDetailGoodsAdapter
    private val orderItemList = mutableListOf<OrderItem>()
    private var status = 0
    override fun getPresenter(): OrderDetailPresenter {
        return OrderDetailPresenter()
    }

    override fun initView() {
        immersionBar {
            statusBarColor(R.color.white)
            statusBarDarkFont(true)
        }

        binding.viewHead.tvTitle.text = "订单详情"


        binding.viewHead.ivBack.setOnClickListener {
            finish()
        }

        orderId = intent.getStringExtra(Constants.ORDER_ID)!!

        orderDetailGoodsAdapter = OrderDetailGoodsAdapter(this, orderItemList)
        binding.mRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.mRecyclerView.adapter = orderDetailGoodsAdapter

        mPresenter!!.orderDetail(orderId)

        binding.tvConfirmLeft.setOnClickListener {
            when (status) {
                1 -> {// 取消订单
                    val builder = AlertDialog.Builder(this)
                    builder.setMessage("您确定要取消订单吗？")
                    builder.setTitle("提示")
                    builder.setPositiveButton("确定") { _, _ ->
                        val orderIdReq = OrderIdReq(orderId)
                        mPresenter!!.orderCancel(orderIdReq)
                    }
                    builder.setNegativeButton("取消") { dialog, _ -> dialog.dismiss() }
                    builder.create().show()
                }
                2 -> {// 取消订单
                    val builder = AlertDialog.Builder(this)
                    builder.setMessage("您确定要取消订单吗？")
                    builder.setTitle("提示")
                    builder.setPositiveButton("确定") { _, _ ->
                        val orderIdReq = OrderIdReq(orderId)
                        mPresenter!!.orderCancel(orderIdReq)
                    }
                    builder.setNegativeButton("取消") { dialog, _ -> dialog.dismiss() }
                    builder.create().show()
                }
                5 -> {//售后

                }
            }
        }

        binding.tvConfirmRight.setOnClickListener {
            when (status) {
                1 -> {// 去支付

                }
                3 -> {// 确认收货
                    val orderIdReq = OrderIdReq(orderId)
                    mPresenter!!.orderConfirm(orderIdReq)
                }
                4 -> {//去评价
                    val bundle = Bundle()
                    bundle.putString(Constants.ORDER_ID, orderId)
                    IntentUtil.get()!!
                        .goActivity(
                            this@OrderDetailActivity,
                            EvaluationActivity::class.java,
                            bundle
                        )
                }
            }
        }
    }

    override fun orderDetail(mData: BaseNetModel<OrderDetailBean>) {
        //binding.tvShopName.text = mData.data.detail.

        binding.tvExpressDelivery.text = mData.data!!.detail.dvy_type
        binding.tvDeliveryCost.text = mData.data!!.detail.order_number
        binding.tvTime.text = mData.data!!.detail.pay_time

        orderItemList.clear()
        orderItemList.addAll(mData.data!!.detail.items)
        orderDetailGoodsAdapter.notifyDataSetChanged()

        status = mData.data!!.detail.status

        when (mData.data!!.detail.status) {// 0:全部 1:待付款 2:待发货 3:待收货 4:待评价 5:成功 6:失败 9:已取消
            1 -> {
                binding.tvStatus.text = "待付款"
                binding.tvConfirmLeft.visibility = View.VISIBLE
                binding.tvConfirmRight.visibility = View.VISIBLE
                binding.tvConfirmLeft.text = "取消订单"
                binding.tvConfirmRight.text = "去支付"
            }

            2 -> {
                binding.tvConfirmLeft.visibility = View.VISIBLE
                binding.tvConfirmRight.visibility = View.GONE
                binding.tvStatus.text = "待发货"
                binding.tvConfirmLeft.text = "取消订单"
            }
            3 -> {
                binding.tvStatus.text = "待收货"
                binding.tvConfirmLeft.visibility = View.GONE
                binding.tvConfirmRight.visibility = View.VISIBLE
                binding.tvConfirmRight.text = "确认收货"
            }
            4 -> {
                binding.tvStatus.text = "待评价"
                binding.tvConfirmLeft.visibility = View.GONE
                binding.tvConfirmRight.visibility = View.VISIBLE
                binding.tvConfirmRight.text = "去评价"

            }
            5 -> {
                binding.tvStatus.text = "已完成"
                binding.tvConfirmLeft.visibility = View.VISIBLE
                binding.tvConfirmRight.visibility = View.GONE
                binding.tvStatus.text = "待发货"
                binding.tvConfirmLeft.text = "售后"
            }
            9 -> binding.tvStatus.text = "已取消"
        }
    }

    override fun orderCancel(mData: BaseNetModel<Any>) {
        mPresenter!!.orderDetail(orderId)
    }

    override fun orderConfirm(mData: BaseNetModel<Any>) {
        mPresenter!!.orderDetail(orderId)
    }

    override fun orderDelete(mData: BaseNetModel<Any>) {
        mPresenter!!.orderDetail(orderId)
    }

    override fun showLoading() {
        showLoadingDialog()
    }

    override fun hideLoading() {
        closeLoadingDialog()
    }
}