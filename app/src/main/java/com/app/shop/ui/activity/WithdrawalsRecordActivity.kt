package com.app.shop.ui.activity

import androidx.recyclerview.widget.LinearLayoutManager
import com.app.shop.adapter.WithdrawalsRecordAdapter
import com.app.shop.base.BaseActivity
import com.app.shop.databinding.ActivityWithdrawalsRecordBinding
import com.app.shop.ui.contract.WithdrawalsRecordContract
import com.app.shop.ui.presenter.WithdrawalsRecordPresenter

/**
 * @author chenshichun
 * 创建日期：2022/8/26
 * 描述：提现记录
 */
class WithdrawalsRecordActivity :
    BaseActivity<ActivityWithdrawalsRecordBinding, WithdrawalsRecordPresenter>(),
    WithdrawalsRecordContract.View {

    private lateinit var withdrawalsRecordAdapter: WithdrawalsRecordAdapter

    override fun getPresenter(): WithdrawalsRecordPresenter {
        return WithdrawalsRecordPresenter()
    }

    override fun initView() {
        withdrawalsRecordAdapter = WithdrawalsRecordAdapter(this, null)
        binding.mRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.mRecyclerView.adapter = withdrawalsRecordAdapter
    }

    override fun showLoading() {
        showLoadingDialog()
    }

    override fun hideLoading() {
        closeLoadingDialog()
    }
}