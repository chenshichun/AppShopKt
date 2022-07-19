package com.app.shop.ui.activity

import com.app.shop.R
import com.app.shop.base.BaseActivity
import com.app.shop.databinding.ActivityMerchantSettledBinding
import com.app.shop.ui.contract.MerchantSettledContract
import com.app.shop.ui.presenter.MerchantSettledPresenter
import com.gyf.immersionbar.ktx.immersionBar

/**
 * @author chenshichun
 * 创建日期：2022/7/19
 * 描述： 商户入驻
 */
class MerchantSettledActivity :
    BaseActivity<ActivityMerchantSettledBinding, MerchantSettledPresenter>(),
    MerchantSettledContract.View {
    override fun getPresenter(): MerchantSettledPresenter {
        return MerchantSettledPresenter()
    }

    override fun initView() {
        immersionBar {
            statusBarColor(R.color.white)
            statusBarDarkFont(true)
        }
        binding.viewHead.ivBack.setOnClickListener{
            finish()
        }
    }
}