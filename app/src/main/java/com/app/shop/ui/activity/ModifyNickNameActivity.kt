package com.app.shop.ui.activity

import com.app.shop.R
import com.app.shop.base.BaseActivity
import com.app.shop.databinding.ActivityModifyNickNameBinding
import com.app.shop.manager.AccountManager
import com.app.shop.ui.contract.ModifyNickNameContract
import com.app.shop.ui.presenter.ModifyNickNamePresenter
import com.gyf.immersionbar.ktx.immersionBar

/**
 * @author chenshichun
 * 创建日期：2022/7/19
 * 描述： 修改昵称
 */
class ModifyNickNameActivity :
    BaseActivity<ActivityModifyNickNameBinding, ModifyNickNamePresenter>(),
    ModifyNickNameContract.View {
    override fun getPresenter(): ModifyNickNamePresenter {
        return ModifyNickNamePresenter()
    }

    override fun initView() {
        immersionBar {
            statusBarColor(R.color.white)
            statusBarDarkFont(true)
        }

        binding.viewHead.tvTitle.text = "修改昵称"
        binding.viewHead.ivBack.setOnClickListener {
            finish()
        }

        binding.tvOldNickName.text = AccountManager.getAccountInfo()!!.nick_name
    }

    override fun showLoading() {
        showLoadingDialog()
    }

    override fun hideLoading() {
        closeLoadingDialog()
    }

}