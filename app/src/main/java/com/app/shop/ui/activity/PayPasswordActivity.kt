package com.app.shop.ui.activity

import android.view.View
import com.app.shop.R
import com.app.shop.base.BaseActivity
import com.app.shop.databinding.ActivityPayPasswordBinding
import com.app.shop.ui.contract.PayPasswordContract
import com.app.shop.ui.presenter.PayPasswordPresenter
import com.app.shop.util.ToastUtil
import com.gyf.immersionbar.ktx.immersionBar

/**
 * @author chenshichun
 * 创建日期：2022/7/25
 * 描述：支付密码
 */
class PayPasswordActivity : BaseActivity<ActivityPayPasswordBinding, PayPasswordPresenter>(),
    PayPasswordContract.View {
    val hasOldPassword = false // 是否设置过密码

    override fun getPresenter(): PayPasswordPresenter {
        return PayPasswordPresenter()
    }

    override fun initView() {
        immersionBar {
            statusBarColor(R.color.white)
            statusBarDarkFont(true)
        }

        binding.viewHead.tvTitle.text = "支付密码"
        binding.viewHead.ivBack.setOnClickListener {
            finish()
        }

        binding.llOldPassword.visibility = if (hasOldPassword) View.VISIBLE else View.GONE
        binding.etPassword.hint = if (hasOldPassword) "请输入新支付密码" else "请设置支付密码"

        binding.btConfirm.setOnClickListener {
            if (hasOldPassword) {
                if (binding.etOldPassword.text.isEmpty()) {
                    ToastUtil.showToast("请输入原支付密码")
                    return@setOnClickListener
                }
                if (binding.etPassword.text.isEmpty()) {
                    ToastUtil.showToast("请输入新密码")
                    return@setOnClickListener
                }
            } else {
                if (binding.etPassword.text.isEmpty()) {
                    ToastUtil.showToast("请设置支付密码")
                    return@setOnClickListener
                }
            }
            if (binding.etNewPassword.text.isEmpty()) {
                ToastUtil.showToast("请设置新密码")
                return@setOnClickListener
            }
            if (!binding.etPassword.text.equals(binding.etNewPassword.text)) {
                ToastUtil.showToast("两次密码不一致")
                return@setOnClickListener
            }
            // TODO
        }
    }

    override fun showLoading() {
        showLoadingDialog()
    }

    override fun hideLoading() {
        closeLoadingDialog()
    }
}