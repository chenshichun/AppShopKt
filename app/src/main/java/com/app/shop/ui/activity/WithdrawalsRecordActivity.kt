package com.app.shop.ui.activity

import androidx.recyclerview.widget.LinearLayoutManager
import com.app.shop.R
import com.app.shop.adapter.WithdrawalsRecordAdapter
import com.app.shop.base.BaseActivity
import com.app.shop.bean.*
import com.app.shop.databinding.ActivityWithdrawalsRecordBinding
import com.app.shop.loadsir.EmptyCallBack
import com.app.shop.ui.contract.WithdrawalsRecordContract
import com.app.shop.ui.presenter.WithdrawalsRecordPresenter
import com.gyf.immersionbar.ktx.immersionBar
import com.kingja.loadsir.core.LoadService
import com.kingja.loadsir.core.LoadSir

/**
 * @author chenshichun
 * 创建日期：2022/8/26
 * 描述：提现记录
 */
class WithdrawalsRecordActivity :
    BaseActivity<ActivityWithdrawalsRecordBinding, WithdrawalsRecordPresenter>(),
    WithdrawalsRecordContract.View {

    private lateinit var withdrawalsRecordAdapter: WithdrawalsRecordAdapter
    private var cashDetailBeanList = mutableListOf<CashDetailBean>()
    private lateinit var loadService: LoadService<Any>
    private var page: Int = 1
    private var size: Int = 10

    override fun getPresenter(): WithdrawalsRecordPresenter {
        return WithdrawalsRecordPresenter()
    }

    override fun initView() {
        immersionBar {
            statusBarColor(R.color.white)
            statusBarDarkFont(true)
        }
        binding.viewHead.tvTitle.text = "提现记录"

        binding.viewHead.ivBack.setOnClickListener {
            finish()
        }

        withdrawalsRecordAdapter = WithdrawalsRecordAdapter(this, cashDetailBeanList)
        binding.mRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.mRecyclerView.adapter = withdrawalsRecordAdapter

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
        mPresenter!!.getCashdetail(page, size)
    }

    override fun getCashdetail(mData: BaseNetModel<CashDetailListBean>) {
        binding.refreshLayout.finishRefresh()
        binding.refreshLayout.finishLoadMore()

        if (page == 1) {
            if (mData.data!!.detail.isEmpty()) {// 空数据
                loadService.showCallback(EmptyCallBack::class.java)
            } else {
                cashDetailBeanList.clear()
                cashDetailBeanList.addAll(mData.data!!.detail)
                withdrawalsRecordAdapter.notifyDataSetChanged()
                loadService.showSuccess()
            }
        } else {
            loadService.showSuccess()
            if (mData.data!!.detail.isEmpty()) {
                page--
            } else {
                cashDetailBeanList.addAll(mData.data!!.detail)
                withdrawalsRecordAdapter.notifyDataSetChanged()
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