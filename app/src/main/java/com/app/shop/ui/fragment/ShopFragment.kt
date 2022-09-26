package com.app.shop.ui.fragment

import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.shop.adapter.OfflineShopAdapter
import com.app.shop.base.BaseFragment
import com.app.shop.bean.BaseNetModel
import com.app.shop.bean.LocalStore
import com.app.shop.bean.LocatStoreListBean
import com.app.shop.databinding.FragmentShopBinding
import com.app.shop.loadsir.EmptyCallBack
import com.app.shop.loadsir.LoadingCallback
import com.app.shop.manager.Constants
import com.app.shop.ui.activity.StoreHomepageActivity
import com.app.shop.ui.contract.ShopContract
import com.app.shop.ui.presenter.ShopPresenter
import com.app.shop.util.AMapUtil
import com.app.shop.util.IntentUtil
import com.kingja.loadsir.core.LoadService
import com.kingja.loadsir.core.LoadSir
import com.lxj.xpopup.XPopup
import kotlinx.coroutines.launch

/**
 * @author chenshichun
 * 创建日期：2022/7/4
 * 描述：本地商家
 *
 */
class ShopFragment : BaseFragment<FragmentShopBinding, ShopPresenter>(), ShopContract.View {
    private lateinit var offlineShopAdapter: OfflineShopAdapter
    val locatStoreList = mutableListOf<LocalStore>()
    private var page: Int = 1
    private var size: Int = 10
    private var dist: Int = 0
    private var search_name: String = ""
    private lateinit var loadService: LoadService<Any>


    override fun initView() {

        binding.etSearch.setOnEditorActionListener { view, _, _ ->
            lifecycleScope.launch {
                search_name = view.text.toString()
                initData()
            }
            true
        }

        offlineShopAdapter = activity?.let { OfflineShopAdapter(it, locatStoreList) }!!
        binding.mRecyclerView.layoutManager = LinearLayoutManager(activity)
        binding.mRecyclerView.adapter = offlineShopAdapter
        offlineShopAdapter.setOnItemClickListener(object : OfflineShopAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                val bundle = Bundle()
                bundle.putString(Constants.SHOP_ID, locatStoreList[position].shop_id)
                IntentUtil.get()!!.goActivity(activity, StoreHomepageActivity::class.java, bundle)
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
                                AMapUtil.openGaoDeMap(
                                    activity!!,
                                    locatStoreList[position].shop_lat,
                                    locatStoreList[position].shop_lng,
                                    locatStoreList[position].province + locatStoreList[position].city
                                            + locatStoreList[position].area + locatStoreList[position].shop_address
                                )
                            }
                            1 -> {// 百度地图
                                AMapUtil.openBaiduMap(
                                    activity!!,
                                    locatStoreList[position].shop_lat,
                                    locatStoreList[position].shop_lng,
                                    locatStoreList[position].province + locatStoreList[position].city
                                            + locatStoreList[position].area + locatStoreList[position].shop_address
                                )
                            }
                            2 -> {//腾讯地图
                                AMapUtil.openTencent(
                                    activity!!,
                                    locatStoreList[position].shop_lat,
                                    locatStoreList[position].shop_lng,
                                    locatStoreList[position].province + locatStoreList[position].city
                                            + locatStoreList[position].area + locatStoreList[position].shop_address
                                )
                            }
                        }
                    }.show()
            }
        })

        loadService = LoadSir.getDefault().register(binding.refreshLayout) {
            initData()
        }

        binding.refreshLayout.setOnRefreshListener {
            page = 1
            initData()
        }

        binding.refreshLayout.setOnLoadMoreListener {
            page++
            initData()
        }
        initData()
    }

    private fun initData() {
        loadService.showCallback(LoadingCallback::class.java)
        mPresenter!!.storeLocalList(page, size, dist, search_name)
    }

    override fun getPresenter(): ShopPresenter {
        return ShopPresenter()
    }

    override fun storeLocalList(mData: BaseNetModel<LocatStoreListBean>) {
        binding.refreshLayout.finishRefresh()
        binding.refreshLayout.finishLoadMore()

        if (page == 1) {
            if (mData.data!!.local_store_list.isEmpty()) {// 空数据
                loadService.showCallback(EmptyCallBack::class.java)
            } else {
                locatStoreList.clear()
                locatStoreList.addAll(mData.data!!.local_store_list)
                offlineShopAdapter.notifyDataSetChanged()
                loadService.showSuccess()
            }
        } else {
            loadService.showSuccess()
            if (mData.data!!.local_store_list.isEmpty()) {
                page--
            } else {
                locatStoreList.addAll(mData.data!!.local_store_list)
                offlineShopAdapter.notifyDataSetChanged()
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