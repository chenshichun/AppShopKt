package com.app.shop.ui.activity

import com.app.shop.R
import com.app.shop.base.BaseActivity
import com.app.shop.bean.BaseNetModel
import com.app.shop.bean.SignBean
import com.app.shop.databinding.ActivitySigninBinding
import com.app.shop.ui.contract.SignInContract
import com.app.shop.ui.presenter.SignInPresenter
import com.gyf.immersionbar.ktx.immersionBar

/**
 * @author chenshichun
 * 创建日期：2022/10/20
 * 描述：
 */
class SignInActvity : BaseActivity<ActivitySigninBinding, SignInPresenter>(), SignInContract.View {
    override fun getPresenter(): SignInPresenter {
        return SignInPresenter()
    }

    override fun initView() {
        immersionBar {
            statusBarColor(R.color.white)
            statusBarDarkFont(true)
        }
        binding.viewHead.tvTitle.text = "签到"
        binding.viewHead.ivBack.setOnClickListener {
            finish()
        }
        binding.btConfirm.setOnClickListener {
            mPresenter!!.sign()
        }
        mPresenter!!.isSign()
    }

    override fun sign(mData: BaseNetModel<Any>) {
        binding.btConfirm.setBackgroundResource(R.drawable.bg_gray_20)
    }

    override fun isSign(mData: BaseNetModel<SignBean>) {
        if (mData.data!!.is_signed) {
            binding.btConfirm.setBackgroundResource(R.drawable.bg_gray_20)
            binding.btConfirm.text = "今日已签到"
        } else {
            binding.btConfirm.setBackgroundResource(R.drawable.confirm_bg)
            binding.btConfirm.text = "签到赚积分"
        }
    }

    override fun showLoading() {
        showLoadingDialog()
    }

    override fun hideLoading() {
        closeLoadingDialog()
    }
}