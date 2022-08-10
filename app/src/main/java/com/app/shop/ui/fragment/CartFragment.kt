package com.app.shop.ui.fragment

import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.shop.adapter.CartAdapter
import com.app.shop.adapter.GoodsAdapter
import com.app.shop.base.BaseFragment
import com.app.shop.databinding.FragmentCartBinding
import com.app.shop.ui.contract.CartContract
import com.app.shop.ui.presenter.CartPresenter

/**
 * @author chenshichun
 * 创建日期：2022/7/4
 * 描述：购物车
 *
 */
class CartFragment : BaseFragment<FragmentCartBinding, CartPresenter>(),
    CartContract.View {
    private lateinit var cartAdapter: CartAdapter
    private var isEdit: Boolean = true //  true 编辑  false 完成
    private lateinit var goodsAdapter: GoodsAdapter

    override fun getPresenter(): CartPresenter {
        return CartPresenter()
    }

    override fun initView() {
        // 购物车
        cartAdapter = activity?.let { CartAdapter(it, null) }!!
        binding.mRecyclerView.layoutManager = LinearLayoutManager(activity)
        binding.mRecyclerView.adapter = cartAdapter
        cartAdapter.setOnItemClickListener(object : CartAdapter.OnItemClickLisenter {
            override fun onItemClick(position: Int) {

            }
        })

        // 猜你喜欢
        goodsAdapter = activity?.let { GoodsAdapter(it, null) }!!
        binding.goodsRv.layoutManager = GridLayoutManager(activity, 2)
        binding.goodsRv.adapter = goodsAdapter
        goodsAdapter.setOnItemClickListener(object : GoodsAdapter.OnItemClickListener{
            override fun onItemClick(position: Int) {
            }
        })

        binding.tvEdit.setOnClickListener {
            if (isEdit) {
                binding.tvEdit.text = "完成"
                binding.llTotal.visibility = View.GONE
                binding.tvConfirm.text = "删除"
            } else {
                binding.tvEdit.text = "编辑"
                binding.llTotal.visibility = View.VISIBLE
                binding.tvConfirm.text = "提交订单"
            }
            isEdit = !isEdit
        }
    }

    override fun showLoading() {
        showLoadingDialog()
    }

    override fun hideLoading() {
        closeLoadingDialog()
    }
}