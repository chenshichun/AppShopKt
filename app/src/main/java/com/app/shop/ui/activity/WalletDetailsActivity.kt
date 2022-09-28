package com.app.shop.ui.activity

import androidx.recyclerview.widget.LinearLayoutManager
import com.app.shop.R
import com.app.shop.adapter.WalletDetailAdapter
import com.app.shop.base.BaseActivity
import com.app.shop.bean.BaseNetModel
import com.app.shop.bean.DetailBean
import com.app.shop.bean.WalletDetailBean
import com.app.shop.databinding.ActivityWalletDetailsBinding
import com.app.shop.loadsir.EmptyCallBack
import com.app.shop.ui.contract.WalletDetailsContract
import com.app.shop.ui.presenter.WalletDetailsPresenter
import com.gyf.immersionbar.ktx.immersionBar
import com.kingja.loadsir.core.LoadService
import com.kingja.loadsir.core.LoadSir

/**
 * @author chenshichun
 * 创建日期：2022/8/26
 * 描述：钱包明细
 */
class WalletDetailsActivity : BaseActivity<ActivityWalletDetailsBinding, WalletDetailsPresenter>(),
    WalletDetailsContract.View {

    private var walletDetailsList = mutableListOf<DetailBean>()
    private lateinit var loadService: LoadService<Any>
    private var page: Int = 1
    private var size: Int = 10
    private lateinit var walletDetailAdapter: WalletDetailAdapter

    override fun getPresenter(): WalletDetailsPresenter {
        return WalletDetailsPresenter()
    }

    override fun initView() {
        immersionBar {
            statusBarColor(R.color.white)
            statusBarDarkFont(true)
        }

        binding.viewHead.tvTitle.text = "钱包明细"
        binding.viewHead.ivBack.setOnClickListener {
            finish()
        }

        walletDetailAdapter = WalletDetailAdapter(this, walletDetailsList)
        binding.mRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.mRecyclerView.adapter = walletDetailAdapter

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
        mPresenter!!.getWalletDetail(page, size)
    }

    override fun getWalletDetail(mData: BaseNetModel<WalletDetailBean>) {
        binding.refreshLayout.finishRefresh()
        binding.refreshLayout.finishLoadMore()

        if (page == 1) {
            if (mData.data!!.detail.isEmpty()) {// 空数据
                loadService.showCallback(EmptyCallBack::class.java)
            } else {
                walletDetailsList.clear()
                walletDetailsList.addAll(mData.data!!.detail)
                walletDetailAdapter.notifyDataSetChanged()
                loadService.showSuccess()
            }
        } else {
            loadService.showSuccess()
            if (mData.data!!.detail.isEmpty()) {
                page--
            } else {
                walletDetailsList.addAll(mData.data!!.detail)
                walletDetailAdapter.notifyDataSetChanged()
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