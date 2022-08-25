package com.app.shop.ui.fragment

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.shop.adapter.OfflineShopAdapter
import com.app.shop.base.BaseFragment
import com.app.shop.databinding.FragmentShopBinding
import com.app.shop.ui.activity.StoreHomepageActivity
import com.app.shop.ui.contract.ShopContract
import com.app.shop.ui.presenter.ShopPresenter
import com.app.shop.util.IntentUtil

/**
 * @author chenshichun
 * 创建日期：2022/7/4
 * 描述：本地商家
 *
 */
class ShopFragment : BaseFragment<FragmentShopBinding, ShopPresenter>(), ShopContract.View {
    private lateinit var offlineShopAdapter: OfflineShopAdapter

    override fun initView() {
        offlineShopAdapter = activity?.let { OfflineShopAdapter(it, null) }!!
        binding.mRecyclerView.layoutManager = LinearLayoutManager(activity)
        binding.mRecyclerView.adapter = offlineShopAdapter
        offlineShopAdapter.setOnItemClickListener(object : OfflineShopAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                IntentUtil.get()!!.goActivity(activity, StoreHomepageActivity::class.java)
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