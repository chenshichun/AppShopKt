package com.app.shop.ui.activity

import com.app.shop.R
import com.app.shop.base.BaseActivity
import com.app.shop.databinding.ActivityAttentionBinding
import com.app.shop.ui.contract.AttentionContract
import com.app.shop.ui.presenter.AttentionPresenter
import com.gyf.immersionbar.ktx.immersionBar
/**
 * @author chenshichun
 * 创建日期：2022/7/4
 * 描述：我的关注
 */
class AttentionActivity : BaseActivity<ActivityAttentionBinding, AttentionPresenter>(),
    AttentionContract.View {
    override fun getPresenter(): AttentionPresenter {
        return AttentionPresenter()
    }

    override fun initView() {
        immersionBar {
            statusBarColor(R.color.white)
            statusBarDarkFont(true)
        }

        binding.viewHead.tvTitle.text = "我的关注"
        binding.viewHead.ivBack.setOnClickListener {
            finish()
        }
    }

}