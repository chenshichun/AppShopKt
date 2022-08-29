package com.app.shop.ui.activity

import android.annotation.SuppressLint
import androidx.recyclerview.widget.GridLayoutManager
import com.app.shop.R
import com.app.shop.adapter.WithdrawPriceAdapter
import com.app.shop.base.BaseActivity
import com.app.shop.bean.WithdrawPriceBean
import com.app.shop.databinding.ActivityWithdrawBinding
import com.app.shop.ui.contract.WithdrawContract
import com.app.shop.ui.presenter.WithdrawPresenter
import com.gyf.immersionbar.ktx.immersionBar

/**
 * @author chenshichun
 * 创建日期：2022/8/26
 * 描述：提现
 */
class WithdrawActivity : BaseActivity<ActivityWithdrawBinding, WithdrawPresenter>(),
    WithdrawContract.View {

    private lateinit var withdrawPriceAdapter: WithdrawPriceAdapter
    private var withdrawPriceList = mutableListOf<WithdrawPriceBean>()
    private val priceList = listOf(10, 20, 30, 50, 100, 200)

    override fun getPresenter(): WithdrawPresenter {
        return WithdrawPresenter()
    }

    override fun initView() {
        immersionBar {
            statusBarColor(R.color.white)
            statusBarDarkFont(true)
        }
        binding.viewHead.tvTitle.text = "提现"

        for (i in priceList) {
            val withdrawPriceBean = WithdrawPriceBean(false, i)
            withdrawPriceList.add(withdrawPriceBean)
        }

        binding.viewHead.ivBack.setOnClickListener {
            finish()
        }

        withdrawPriceAdapter = WithdrawPriceAdapter(this, withdrawPriceList)
        binding.mRecyclerView.layoutManager = GridLayoutManager(this, 3)
        binding.mRecyclerView.adapter = withdrawPriceAdapter
        withdrawPriceAdapter.setOnItemClickListener(object :
            WithdrawPriceAdapter.OnItemClickListener {
            @SuppressLint("NotifyDataSetChanged")
            override fun onItemClick(position: Int) {
                for (withdrawPriceBean in withdrawPriceList) {
                    withdrawPriceBean.isCheck = false
                }
                withdrawPriceList[position].isCheck = true
                withdrawPriceAdapter.notifyDataSetChanged()
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