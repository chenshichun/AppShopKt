package com.app.shop.ui.activity

import com.app.shop.R
import com.app.shop.base.BaseActivity
import com.app.shop.databinding.ActivityOperationCenterApplyBinding
import com.app.shop.ui.contract.OperationCenterApplyContract
import com.app.shop.ui.presenter.OperationCenterApplyPresenter
import com.gyf.immersionbar.ktx.immersionBar

/**
 * @author chenshichun
 * 创建日期：2022/8/29
 * 描述：运营中心申请
 */
class OperationCenterApplyActivity :
    BaseActivity<ActivityOperationCenterApplyBinding, OperationCenterApplyPresenter>(),
    OperationCenterApplyContract.View {
    override fun getPresenter(): OperationCenterApplyPresenter {
        return OperationCenterApplyPresenter()
    }

    override fun initView() {
        immersionBar {
            statusBarColor(R.color.white)
            statusBarDarkFont(true)
        }

        binding.viewHead.tvTitle.text = "运营中心申请"
        binding.viewHead.ivBack.setOnClickListener {
            finish()
        }

        
    }

    override fun showLoading() {
        showLoadingDialog()
    }

    override fun hideLoading() {
        closeLoadingDialog()
    }
}