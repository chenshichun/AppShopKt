package com.app.shop.ui.fragment

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.shop.adapter.OrderAdapter
import com.app.shop.base.BaseFragment
import com.app.shop.bean.*
import com.app.shop.databinding.FragmentOrderBinding
import com.app.shop.loadsir.EmptyCallBack
import com.app.shop.manager.Constants
import com.app.shop.req.OrderIdReq
import com.app.shop.ui.activity.*
import com.app.shop.ui.contract.OrderFragmentContract
import com.app.shop.ui.presenter.OrderFragmentPresenter
import com.app.shop.util.IntentUtil
import com.kingja.loadsir.core.LoadService
import com.kingja.loadsir.core.LoadSir
import com.orhanobut.logger.Logger

/**
 * @author chenshichun
 * 创建日期：2022/7/18
 * 描述：订单fragment
 */
class OrderFragment(val status: Int) : BaseFragment<FragmentOrderBinding, OrderFragmentPresenter>(),
    OrderFragmentContract.View {

    private lateinit var orderAdapter: OrderAdapter
    private var orderBeanList = mutableListOf<Order>()
    private lateinit var loadService: LoadService<Any>

    override fun initView() {
        orderAdapter = activity?.let { OrderAdapter(it, orderBeanList) }!!
        binding.mRecyclerView.layoutManager = LinearLayoutManager(activity)
        binding.mRecyclerView.adapter = orderAdapter
        orderAdapter.setOnItemClickListener(object : OrderAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                val bundle = Bundle()
                bundle.putString(Constants.ORDER_ID, orderBeanList[position].order_id)
                Logger.d(position)

                Logger.d(orderBeanList[position].order_id)
                IntentUtil.get()!!.goActivity(activity, OrderDetailActivity::class.java, bundle)
            }

            override fun onLeftClick(position: Int) {
                when (orderBeanList[position].status) {
                    1 -> {// 待付款-取消订单
                        val builder = AlertDialog.Builder(activity!!)
                        builder.setMessage("您确定要取消订单吗？")
                        builder.setTitle("提示")
                        builder.setPositiveButton("确定") { _, _ ->
                            val orderIdReq = OrderIdReq(orderBeanList[position].order_id)
                            mPresenter!!.orderCancel(orderIdReq)
                        }
                        builder.setNegativeButton("取消") { dialog, _ -> dialog.dismiss() }
                        builder.create().show()
                    }
                }
            }

            override fun onRightClick(position: Int) {
                when (orderBeanList[position].status) {
                    1 -> {// 待付款-去付款
                        if (orderBeanList[position].items.size == 1) {// 单商品付款
                            val orderInfoBean = OrderInfoBean(
                                orderBeanList[position].items[0].prod_name,
                                orderBeanList[position].items[0].point,
                                orderBeanList[position].items[0].price,
                                orderBeanList[position].items[0].pic,
                                orderBeanList[position].order_id
                            )
                            IntentUtil.get()!!
                                .goActivity(activity, PayOrderActivity::class.java, orderInfoBean)
                        } else {// 多商品付款
                            /*val cartOrderDetailBeans: List<CartOrderDetailBean> = arrayListOf()

                            IntentUtil.get()!!
                                .goActivity(activity, PayCartOrderActivity::class.java, cartOrderDetailBeans)*/

                        }
                    }
                    2 -> {// 待发货-取消订单
                        val builder = AlertDialog.Builder(activity!!)
                        builder.setMessage("您确定要取消订单吗？")
                        builder.setTitle("提示")
                        builder.setPositiveButton("确定") { _, _ ->
                            val orderIdReq = OrderIdReq(orderBeanList[position].order_id)
                            mPresenter!!.orderCancel(orderIdReq)
                        }
                        builder.setNegativeButton("取消") { dialog, _ -> dialog.dismiss() }
                        builder.create().show()
                    }
                    3 -> {// 待收货-确认收货
                        val orderIdReq = OrderIdReq(orderBeanList[position].order_id)
                        mPresenter!!.orderConfirm(orderIdReq)
                    }
                    4 -> {// 待评价-去评价
                        val bundle = Bundle()
                        bundle.putString(Constants.ORDER_ID, orderBeanList!![position].order_id)
                        IntentUtil.get()!!
                            .goActivity(activity, EvaluationActivity::class.java, bundle)
                    }
                }
            }
        })
        loadService = LoadSir.getDefault().register(binding.refreshLayout) {
            mPresenter!!.orderList(status)

        }
        binding.refreshLayout.setEnableLoadMore(false)
        binding.refreshLayout.setOnRefreshListener {
            mPresenter!!.orderList(status)
        }
        mPresenter!!.orderList(status)
    }

    override fun onResume() {
        super.onResume()
    }

    override fun getPresenter(): OrderFragmentPresenter {
        return OrderFragmentPresenter()
    }

    override fun orderList(mData: BaseNetModel<OrderListBean>) {

        binding.refreshLayout.finishRefresh()
        if (mData.data!!.orders.isNotEmpty()) {
            orderBeanList.clear()
            orderBeanList.addAll(mData.data!!.orders)
            orderAdapter.notifyDataSetChanged()
            loadService.showSuccess()
        } else {
            loadService.showCallback(EmptyCallBack::class.java)
        }
    }

    override fun orderCancel(mData: BaseNetModel<Any>) {
        mPresenter!!.orderList(status)
    }

    override fun orderConfirm(mData: BaseNetModel<Any>) {
        mPresenter!!.orderList(status)
    }

    override fun orderDelete(mData: BaseNetModel<Any>) {
        mPresenter!!.orderList(status)
    }

    override fun showLoading() {
        showLoadingDialog()
    }

    override fun hideLoading() {
        closeLoadingDialog()
    }
}