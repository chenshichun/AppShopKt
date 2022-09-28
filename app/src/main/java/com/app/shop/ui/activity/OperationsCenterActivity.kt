package com.app.shop.ui.activity

import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.shop.R
import com.app.shop.adapter.OfflineShopAdapter
import com.app.shop.base.BaseActivity
import com.app.shop.bean.BaseNetModel
import com.app.shop.bean.LocalStore
import com.app.shop.bean.ServiceStoreListBean
import com.app.shop.databinding.ActivityOperationsCenterBinding
import com.app.shop.loadsir.EmptyCallBack
import com.app.shop.loadsir.LoadingCallback
import com.app.shop.manager.Constants
import com.app.shop.ui.contract.OperationsCenterContract
import com.app.shop.ui.presenter.OperationsCenterPresenter
import com.app.shop.util.AMapUtil
import com.app.shop.util.IntentUtil
import com.gyf.immersionbar.ktx.immersionBar
import com.kingja.loadsir.core.LoadService
import com.kingja.loadsir.core.LoadSir
import com.lxj.xpopup.XPopup
import kotlinx.coroutines.launch

/**
 * @author chenshichun
 * 创建日期：2022/8/1
 * 描述：运营中心
 */
class OperationsCenterActivity :
    BaseActivity<ActivityOperationsCenterBinding, OperationsCenterPresenter>(),
    OperationsCenterContract.View {

    private lateinit var offlineShopAdapter: OfflineShopAdapter

    val locatStoreList = mutableListOf<LocalStore>()
    private var page: Int = 1
    private var size: Int = 10
    private var dist: Int = 0
    private var search_name: String = ""
    private lateinit var loadService: LoadService<Any>

    override fun getPresenter(): OperationsCenterPresenter {
        return OperationsCenterPresenter()
    }

    override fun initView() {
        immersionBar {
            statusBarColor(R.color.white)
            statusBarDarkFont(true)
        }
        binding.viewHead.tvTitle.text = "运营中心"

        binding.viewHead.ivBack.setOnClickListener {
            finish()
        }

        binding.etSearch.setOnEditorActionListener { view, _, _ ->
            lifecycleScope.launch {
                search_name = view.text.toString()
                initData()
            }
            true
        }

        binding.tvSearch.setOnClickListener {
            search_name = binding.tvSearch.text.toString()
            initData()
        }

        offlineShopAdapter = OfflineShopAdapter(this, locatStoreList)
        binding.mRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.mRecyclerView.adapter = offlineShopAdapter
        offlineShopAdapter.setOnItemClickListener(object : OfflineShopAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                val bundle = Bundle()
                bundle.putString(Constants.SHOP_ID, locatStoreList[position].shop_id)
                IntentUtil.get()!!
                    .goActivity(this@OperationsCenterActivity, StoreHomepageActivity::class.java,bundle)
            }

            override fun onNavigationClick(position: Int) {
                XPopup.Builder(this@OperationsCenterActivity)
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
                                    this@OperationsCenterActivity,
                                    "",
                                    "",
                                    "义乌市国际商务服务中心"
                                )
                            }
                            1 -> {// 百度地图
                                AMapUtil.openBaiduMap(
                                    this@OperationsCenterActivity,
                                    "",
                                    "",
                                    "义乌市国际商务服务中心"
                                )
                            }
                            2 -> {//腾讯地图
                                AMapUtil.openTencent(
                                    this@OperationsCenterActivity,
                                    "",
                                    "",
                                    "义乌市国际商务服务中心"
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
        mPresenter!!.storeServiceList(page, size, dist, search_name)
    }

    override fun storeServiceList(mData: BaseNetModel<ServiceStoreListBean>) {
        binding.refreshLayout.finishRefresh()
        binding.refreshLayout.finishLoadMore()

        if (page == 1) {
            if (mData.data!!.service_center_list.isEmpty()) {// 空数据
                loadService.showCallback(EmptyCallBack::class.java)
            } else {
                locatStoreList.clear()
                locatStoreList.addAll(mData.data!!.service_center_list)
                offlineShopAdapter.notifyDataSetChanged()
                loadService.showSuccess()
            }
        } else {
            loadService.showSuccess()
            if (mData.data!!.service_center_list.isEmpty()) {
                page--
            } else {
                locatStoreList.addAll(mData.data!!.service_center_list)
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