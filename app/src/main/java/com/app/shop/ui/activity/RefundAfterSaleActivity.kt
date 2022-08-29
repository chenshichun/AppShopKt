package com.app.shop.ui.activity

import androidx.recyclerview.widget.LinearLayoutManager
import com.app.shop.R
import com.app.shop.adapter.RefundAfterSaleAdapter
import com.app.shop.base.BaseActivity
import com.app.shop.databinding.ActivityRefundAfterSaleBinding
import com.app.shop.ui.contract.RefundAfterSaleContract
import com.app.shop.ui.presenter.RefundAfterSalePresenter
import com.gyf.immersionbar.ktx.immersionBar

/*
* 退款售后
* */
class RefundAfterSaleActivity :
    BaseActivity<ActivityRefundAfterSaleBinding, RefundAfterSalePresenter>(),
    RefundAfterSaleContract.View {

    private lateinit var refundAfterSaleAdapter: RefundAfterSaleAdapter

    override fun getPresenter(): RefundAfterSalePresenter {
        return RefundAfterSalePresenter()
    }

    override fun initView() {
        immersionBar {
            statusBarColor(R.color.white)
            statusBarDarkFont(true)
        }

        binding.viewHead.tvTitle.text = "退款售后"
        binding.viewHead.ivBack.setOnClickListener {
            finish()
        }

        refundAfterSaleAdapter = RefundAfterSaleAdapter(this, null)
        binding.mRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.mRecyclerView.adapter = refundAfterSaleAdapter

    }

    override fun showLoading() {
        showLoadingDialog()
    }

    override fun hideLoading() {
        closeLoadingDialog()
    }
}