package com.app.shop.ui.activity

import android.os.Bundle
import android.view.View
import com.app.shop.R
import com.app.shop.base.BaseActivity
import com.app.shop.bean.BaseNetModel
import com.app.shop.bean.WalletBean
import com.app.shop.databinding.ActivityMyPurseBinding
import com.app.shop.manager.Constants
import com.app.shop.ui.contract.MyPurseContract
import com.app.shop.ui.presenter.MyPursePresenter
import com.app.shop.util.IntentUtil
import com.gyf.immersionbar.ktx.immersionBar

/**
 * @author chenshichun
 * 创建日期：2022/8/26
 * 描述：我的钱包
 */
class MyPurseActivity : BaseActivity<ActivityMyPurseBinding, MyPursePresenter>(),
    MyPurseContract.View, View.OnClickListener {
    override fun getPresenter(): MyPursePresenter {
        return MyPursePresenter()
    }

    override fun initView() {
        immersionBar {
            statusBarColor(R.color.white)
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

        mPresenter!!.walletInfo()
    }

    override fun walletInfo(mData: BaseNetModel<WalletBean>) {
        binding.tvTotalAssets.text = mData.data!!.wallet.cash
        binding.tvAmountCanWithdrawn.text = mData.data!!.wallet.avail_cash
        binding.tvWithdrawnAmount.text = mData.data!!.wallet.accu_cash
        binding.tvBalanceAmount.text = mData.data!!.wallet.cash
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
                val bundle = Bundle()
                bundle.putString(Constants.PRICE, binding.tvAmountCanWithdrawn.text.toString())
                IntentUtil.get()!!.goActivity(this, WithdrawActivity::class.java, bundle)
            }
            R.id.stv_1 -> {// 钱包明细
                IntentUtil.get()!!.goActivity(this, WalletDetailsActivity::class.java)
            }
            R.id.stv_2 -> {// 修改提现账号
                IntentUtil.get()!!.goActivity(this, ReflectAccountActivity::class.java)
            }
            R.id.stv_3 -> {// 提现记录
                IntentUtil.get()!!.goActivity(this, WithdrawalsRecordActivity::class.java)
            }
        }
    }
}