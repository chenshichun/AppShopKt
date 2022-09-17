package com.app.shop.ui.activity

import android.annotation.SuppressLint
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.shop.R
import com.app.shop.adapter.NewInStoreAdapter
import com.app.shop.adapter.RecentAttentionAdapter
import com.app.shop.base.BaseActivity
import com.app.shop.bean.BaseNetModel
import com.app.shop.bean.SotreListBean
import com.app.shop.bean.Store
import com.app.shop.databinding.ActivityAttentionBinding
import com.app.shop.loadsir.EmptyCallBack
import com.app.shop.loadsir.ErrorCallback
import com.app.shop.loadsir.NetWorkErrorCallBack
import com.app.shop.ui.contract.AttentionContract
import com.app.shop.ui.presenter.AttentionPresenter
import com.gyf.immersionbar.ktx.immersionBar
import com.kingja.loadsir.core.LoadService
import com.kingja.loadsir.core.LoadSir

/**
 * @author chenshichun
 * 创建日期：2022/7/4
 * 描述：我的关注
 */
class AttentionActivity : BaseActivity<ActivityAttentionBinding, AttentionPresenter>(),
    AttentionContract.View {

    private lateinit var recentAttentionAdapter: RecentAttentionAdapter
    private lateinit var newInStoreAdapter: NewInStoreAdapter
    private lateinit var loadService: LoadService<Any>

    private var storeList = mutableListOf<Store>()

    override fun getPresenter(): AttentionPresenter {
        return AttentionPresenter()
    }

    override fun initView() {
        immersionBar {
            statusBarColor(R.color.white)
            statusBarDarkFont(true)
        }

        binding.viewHead.tvTitle.text = "我的关注"
        binding.viewHead.ivBack.setOnClickListener {
            finish()
        }

        recentAttentionAdapter = RecentAttentionAdapter(this, storeList)
        binding.rvRecentAttention.layoutManager = GridLayoutManager(this, 4)
        binding.rvRecentAttention.adapter = recentAttentionAdapter

        newInStoreAdapter = NewInStoreAdapter(this, storeList)
        binding.rvNewInStore.layoutManager = LinearLayoutManager(this)
        binding.rvNewInStore.adapter = newInStoreAdapter

        loadService = LoadSir.getDefault().register(binding.refreshLayout) {
            mPresenter!!.storeList()
        }
        binding.refreshLayout.setEnableLoadMore(false)
        binding.refreshLayout.setOnRefreshListener {
            mPresenter!!.storeList()
        }
    }

    override fun onResume() {
        mPresenter!!.storeList()
        super.onResume()
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun storeList(mData: BaseNetModel<SotreListBean>) {
        binding.refreshLayout.finishRefresh()
        if (mData.data!!.store_list.isNotEmpty()) {
            storeList.clear()
            storeList.addAll(mData.data!!.store_list)
            recentAttentionAdapter.notifyDataSetChanged()
            newInStoreAdapter.notifyDataSetChanged()
            loadService.showSuccess()
        } else {
            loadService.showCallback(EmptyCallBack::class.java)
        }
    }

    override fun noNetworkView() {
        loadService.showCallback(NetWorkErrorCallBack::class.java)
    }

    override fun showError() {
        loadService.showCallback(ErrorCallback::class.java)
    }

    override fun showLoading() {
        showLoadingDialog()
    }

    override fun hideLoading() {
        closeLoadingDialog()
    }
}