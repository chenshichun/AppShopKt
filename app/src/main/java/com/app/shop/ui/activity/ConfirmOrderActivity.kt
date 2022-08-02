package com.app.shop.ui.activity

import com.app.shop.R
import com.app.shop.base.BaseActivity
import com.app.shop.databinding.ActivityConfirmOrderBinding
import com.app.shop.ui.contract.ConfirmOrderContract
import com.app.shop.ui.presenter.ConfirmOrderPresenter
import com.app.shop.util.IntentUtil
import com.gyf.immersionbar.ktx.immersionBar

/**
 * @author chenshichun
 * 创建日期：2022/7/5
 * 描述：确认订单
 */
class ConfirmOrderActivity : BaseActivity<ActivityConfirmOrderBinding, ConfirmOrderPresenter>(),
    ConfirmOrderContract.View {
    override fun getPresenter(): ConfirmOrderPresenter {
        return ConfirmOrderPresenter()
    }

    override fun initView() {
        immersionBar {
            statusBarColor(R.color.white)
            statusBarDarkFont(true)
        }

        binding.viewHead.tvTitle.text = "确认订单"
        binding.viewHead.ivBack.setOnClickListener {
            finish()
        }

        binding.llAddress.setOnClickListener {
            IntentUtil.get()!!.goActivityResult(this, AddressListActivity::class.java, 100)
        }

        binding.tvConfirm.setOnClickListener {
            IntentUtil.get()!!.goActivity(this, PayOrderActivity::class.java)
        }
    }

    override fun showLoading() {
        showLoadingDialog()
    }

    override fun hideLoading() {
        closeLoadingDialog()
    }

}