package com.app.shop.ui.activity

import android.view.View
import com.app.shop.R
import com.app.shop.base.BaseActivity
import com.app.shop.databinding.ActivityMyPurseBinding
import com.app.shop.ui.contract.MyPurseContract
import com.app.shop.ui.presenter.MyPursePresenter
import com.app.shop.util.IntentUtil
import com.gyf.immersionbar.ktx.immersionBar

/*
* 我的钱包
* */
class MyPurseActivity : BaseActivity<ActivityMyPurseBinding, MyPursePresenter>(),
    MyPurseContract.View, View.OnClickListener {
    override fun getPresenter(): MyPursePresenter {
        return MyPursePresenter()
    }

    override fun initView() {
        immersionBar {
            statusBarDarkFont(true)
        }
        binding.viewHead.tvTitle.text = "我的钱包"

        binding.viewHead.ivBack.setOnClickListener {
            finish()
        }

        binding.tvWithdraw.setOnClickListener(this)
        binding.stv1.setOnClickListener(this)
        binding.stv2.setOnClickListener(this)
        binding.stv3.setOnClickListener(this)
    }

    override fun showLoading() {
        showLoadingDialog()
    }

    override fun hideLoading() {
        closeLoadingDialog()
    }

    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.tv_withdraw -> {// 提现
                IntentUtil.get()!!.goActivity(this, WithdrawActivity::class.java)
            }
            R.id.stv_1 -> {

            }
            R.id.stv_2 -> {// 修改提现账号
                IntentUtil.get()!!.goActivity(this, ReflectAccountActivity::class.java)
            }
            R.id.stv_3 -> {

            }
        }
    }
}