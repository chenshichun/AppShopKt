package com.app.shop.ui.fragment

import androidx.recyclerview.widget.GridLayoutManager
import com.app.shop.adapter.CartGoodsAdapter
import com.app.shop.adapter.OfflineShopAdapter
import com.app.shop.databinding.FragmentShopBinding
import com.app.shop.base.BaseFragment
import com.app.shop.ui.contract.ShopContract
import com.app.shop.ui.presenter.ShopPresenter

/**
 * @author chenshichun
 * 创建日期：2022/7/4
 * 描述：线下联盟商店
 *
 */
class ShopFragment : BaseFragment<FragmentShopBinding, ShopPresenter>(), ShopContract.View {
    private lateinit var offlineShopAdapter: OfflineShopAdapter

    override fun initView() {
        offlineShopAdapter = activity?.let { OfflineShopAdapter(it, null) }!!
        binding.mRecyclerView.layoutManager = GridLayoutManager(activity, 2)
        binding.mRecyclerView.adapter = offlineShopAdapter
        offlineShopAdapter.setOnItemClickListener(object : OfflineShopAdapter.OnItemClickListener{
            override fun onItemClick(position: Int) {
            }
        })
    }

    override fun getPresenter(): ShopPresenter {
        return ShopPresenter()
    }

    override fun showLoading() {
        showLoadingDialog()
    }

    override fun hideLoading() {
        closeLoadingDialog()
    }
}