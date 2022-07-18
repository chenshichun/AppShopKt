package com.app.shop.ui.fragment

import androidx.recyclerview.widget.LinearLayoutManager
import com.app.shop.adapter.OrderAdapter
import com.app.shop.base.BaseFragment
import com.app.shop.databinding.FragmentOrderBinding
import com.app.shop.ui.contract.OrderFragmentContract
import com.app.shop.ui.presenter.OrderFragmentPresenter

/**
 * @author chenshichun
 * 创建日期：2022/7/18
 * 描述：订单fragment
 */
class OrderFragment : BaseFragment<FragmentOrderBinding, OrderFragmentPresenter>(),
    OrderFragmentContract.View {

    private lateinit var orderAdapter: OrderAdapter

    override fun initView() {
        orderAdapter = activity?.let { OrderAdapter(it, null) }!!
        binding.mRecyclerView.layoutManager = LinearLayoutManager(activity)
        binding.mRecyclerView.adapter = orderAdapter
    }

    override fun getPresenter(): OrderFragmentPresenter {
        return OrderFragmentPresenter()
    }
}