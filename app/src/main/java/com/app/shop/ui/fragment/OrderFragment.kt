package com.app.shop.ui.fragment

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.shop.adapter.OrderAdapter
import com.app.shop.base.BaseFragment
import com.app.shop.bean.BaseNetModel
import com.app.shop.bean.Order
import com.app.shop.bean.OrderListBean
import com.app.shop.databinding.FragmentOrderBinding
import com.app.shop.manager.Constants
import com.app.shop.ui.activity.EvaluationActivity
import com.app.shop.ui.activity.OrderDetailActivity
import com.app.shop.ui.contract.OrderFragmentContract
import com.app.shop.ui.presenter.OrderFragmentPresenter
import com.app.shop.util.IntentUtil

/**
 * @author chenshichun
 * 创建日期：2022/7/18
 * 描述：订单fragment
 */
class OrderFragment(val status: Int) : BaseFragment<FragmentOrderBinding, OrderFragmentPresenter>(),
    OrderFragmentContract.View {

    private lateinit var orderAdapter: OrderAdapter
    private var orderBeanList = mutableListOf<Order>()

    override fun initView() {
        orderAdapter = activity?.let { OrderAdapter(it, orderBeanList) }!!
        binding.mRecyclerView.layoutManager = LinearLayoutManager(activity)
        binding.mRecyclerView.adapter = orderAdapter
        orderAdapter.setOnItemClickListener(object : OrderAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                val bundle = Bundle()
                bundle.putString(Constants.ORDER_ID, orderBeanList!![position].order_id)
                IntentUtil.get()!!.goActivity(activity, OrderDetailActivity::class.java,bundle)
            }

            override fun onLeftClick(position: Int) {
                when (orderBeanList[position].status) {
                    1 -> {// 待付款-取消订单

                    }
                }
            }

            override fun onRightClick(position: Int) {
                when (orderBeanList[position].status) {
                    1 -> {// 待付款-去付款

                    }
                    2 -> {// 待发货-取消订单

                    }
                    3 -> {// 待收货-确认收货

                    }
                    4 -> {// 待评价-去评价
                        IntentUtil.get()!!.goActivity(activity, EvaluationActivity::class.java)
                    }
                }
            }
        })

        mPresenter!!.orderList(status)
    }

    override fun onResume() {
        super.onResume()
    }

    override fun getPresenter(): OrderFragmentPresenter {
        return OrderFragmentPresenter()
    }

    override fun orderList(mData: BaseNetModel<OrderListBean>) {
        orderBeanList.clear()
        orderBeanList.addAll(mData.data!!.orders)
        orderAdapter.notifyDataSetChanged()
    }

    override fun showLoading() {
        showLoadingDialog()
    }

    override fun hideLoading() {
        closeLoadingDialog()
    }
}