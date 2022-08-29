package com.app.shop.ui.activity

import com.app.shop.R
import com.app.shop.base.BaseActivity
import com.app.shop.databinding.ActivityStoreImpressionBinding
import com.app.shop.ui.contract.StoreImpressionContract
import com.app.shop.ui.presenter.StoreImpressionPresenter
import com.app.shop.util.IntentUtil
import com.gyf.immersionbar.ktx.immersionBar

/**
 * @author chenshichun
 * 创建日期：2022/8/29
 * 描述：店铺印象
 */
class StoreImpressionActivity :
    BaseActivity<ActivityStoreImpressionBinding, StoreImpressionPresenter>(),
    StoreImpressionContract.View {
    override fun getPresenter(): StoreImpressionPresenter {
        return StoreImpressionPresenter()
    }

    override fun initView() {
        immersionBar {
            statusBarColor(R.color.white)
            statusBarDarkFont(true)
        }

        binding.viewHead.tvTitle.text = "店铺印象"
        binding.viewHead.ivBack.setOnClickListener {
            finish()
        }

        binding.tvStoreComplaint.setOnClickListener {
            IntentUtil.get()!!.goActivity(this, ComplaintActivity::class.java)
        }
    }

    override fun showLoading() {
        showLoadingDialog()
    }

    override fun hideLoading() {
        closeLoadingDialog()
    }
}