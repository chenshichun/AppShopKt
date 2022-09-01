package com.app.shop.ui.fragment

import androidx.recyclerview.widget.LinearLayoutManager
import com.app.shop.adapter.OfflineShopAdapter
import com.app.shop.base.BaseFragment
import com.app.shop.databinding.FragmentShopBinding
import com.app.shop.ui.activity.StoreHomepageActivity
import com.app.shop.ui.contract.ShopContract
import com.app.shop.ui.presenter.ShopPresenter
import com.app.shop.util.AMapUtil
import com.app.shop.util.IntentUtil
import com.lxj.xpopup.XPopup

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

            override fun onNavigationClick(position: Int) {// 导航
                XPopup.Builder(context)
                    .isDestroyOnDismiss(true)
                    .isDarkTheme(false)
                    .hasShadowBg(true)
                    .moveUpToKeyboard(false)
                    .isCoverSoftInput(true)
                    .asBottomList(
                        "",
                        arrayOf("高德地图", "百度地图", "腾讯地图")
                    ) { position, _ ->
                        when (position) {
                            0 -> {// 高德地图
                                AMapUtil.openGaoDeMap(activity!!, "", "", "义乌市国际商务服务中心")
                            }
                            1 -> {// 百度地图
                                AMapUtil.openBaiduMap(activity!!, "", "", "义乌市国际商务服务中心")
                            }
                            2 -> {//腾讯地图
                                AMapUtil.openTencent(activity!!, "", "", "义乌市国际商务服务中心")
                            }
                        }
                    }.show()
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