package com.app.shop.ui.activity

import androidx.recyclerview.widget.LinearLayoutManager
import com.app.shop.R
import com.app.shop.adapter.OfflineShopAdapter
import com.app.shop.base.BaseActivity
import com.app.shop.databinding.ActivityOperationsCenterBinding
import com.app.shop.ui.contract.OperationsCenterContract
import com.app.shop.ui.presenter.OperationsCenterPresenter
import com.app.shop.util.AMapUtil
import com.app.shop.util.IntentUtil
import com.gyf.immersionbar.ktx.immersionBar
import com.lxj.xpopup.XPopup

/**
 * @author chenshichun
 * 创建日期：2022/8/1
 * 描述：运营中心
 */
class OperationsCenterActivity :
    BaseActivity<ActivityOperationsCenterBinding, OperationsCenterPresenter>(),
    OperationsCenterContract.View {

    private lateinit var offlineShopAdapter: OfflineShopAdapter

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

        offlineShopAdapter = OfflineShopAdapter(this, null)
        binding.mRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.mRecyclerView.adapter = offlineShopAdapter
        offlineShopAdapter.setOnItemClickListener(object : OfflineShopAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                IntentUtil.get()!!
                    .goActivity(this@OperationsCenterActivity, StoreHomepageActivity::class.java)
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
    }

    override fun showLoading() {
        showLoadingDialog()
    }

    override fun hideLoading() {
        closeLoadingDialog()
    }
}