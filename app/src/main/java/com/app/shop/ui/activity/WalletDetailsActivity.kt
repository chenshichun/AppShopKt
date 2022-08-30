package com.app.shop.ui.activity

import androidx.recyclerview.widget.LinearLayoutManager
import com.app.shop.R
import com.app.shop.adapter.WalletDetailAdapter
import com.app.shop.base.BaseActivity
import com.app.shop.databinding.ActivityWalletDetailsBinding
import com.app.shop.ui.contract.WalletDetailsContract
import com.app.shop.ui.presenter.WalletDetailsPresenter
import com.gyf.immersionbar.ktx.immersionBar

/**
 * @author chenshichun
 * 创建日期：2022/8/26
 * 描述：钱包明细
 */
class WalletDetailsActivity : BaseActivity<ActivityWalletDetailsBinding, WalletDetailsPresenter>(),
    WalletDetailsContract.View {

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

        walletDetailAdapter = WalletDetailAdapter(this, null)
        binding.mRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.mRecyclerView.adapter = walletDetailAdapter
    }

    override fun showLoading() {
        showLoadingDialog()
    }

    override fun hideLoading() {
        closeLoadingDialog()
    }
}