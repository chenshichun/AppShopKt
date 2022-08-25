package com.app.shop.ui.activity

import androidx.recyclerview.widget.LinearLayoutManager
import com.app.shop.R
import com.app.shop.adapter.OfflineShopAdapter
import com.app.shop.base.BaseActivity
import com.app.shop.databinding.ActivityOperationsCenterBinding
import com.app.shop.ui.contract.OperationsCenterContract
import com.app.shop.ui.presenter.OperationsCenterPresenter
import com.app.shop.util.IntentUtil
import com.gyf.immersionbar.ktx.immersionBar

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
                IntentUtil.get()!!.goActivity(this@OperationsCenterActivity, StoreHomepageActivity::class.java)
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