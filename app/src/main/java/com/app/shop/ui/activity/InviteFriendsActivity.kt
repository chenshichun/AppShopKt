package com.app.shop.ui.activity

import com.app.shop.R
import com.app.shop.base.BaseActivity
import com.app.shop.databinding.ActivityInviteFriendsBinding
import com.app.shop.ui.contract.InviteFriendsContract
import com.app.shop.ui.presenter.InviteFriendsPresenter
import com.gyf.immersionbar.ktx.immersionBar

class InviteFriendsActivity : BaseActivity<ActivityInviteFriendsBinding, InviteFriendsPresenter>(),
    InviteFriendsContract.View {
    override fun getPresenter(): InviteFriendsPresenter {
        return InviteFriendsPresenter()
    }

    override fun initView() {
        immersionBar {
            statusBarColor(R.color.white)
            statusBarDarkFont(true)
        }
        binding.viewHead.ivBack.setOnClickListener{
            finish()
        }
        binding.viewHead.tvTitle.text = "邀请赚钱"
    }

    override fun showLoading() {
        showLoadingDialog()
    }

    override fun hideLoading() {
        closeLoadingDialog()
    }
}