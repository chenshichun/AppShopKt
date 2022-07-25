package com.app.shop.ui.activity

import android.content.Intent
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.navigation.Navigation
import com.app.shop.R
import com.app.shop.base.BaseActivity
import com.app.shop.databinding.ActivityAccountSecurityBinding
import com.app.shop.manager.AccountManager
import com.app.shop.ui.contract.AccountSecurityContract
import com.app.shop.ui.presenter.AccountSecurityPresenter
import com.gyf.immersionbar.ktx.immersionBar

/**
 * @author chenshichun
 * 创建日期：2022/7/4
 * 描述：账号安全
 */
class AccountSecurityActivity :
    BaseActivity<ActivityAccountSecurityBinding, AccountSecurityPresenter>(),
    AccountSecurityContract.View, View.OnClickListener {
    override fun getPresenter(): AccountSecurityPresenter {
        return AccountSecurityPresenter()
    }

    override fun initView() {
        immersionBar {
            statusBarColor(R.color.white)
            statusBarDarkFont(true)
        }

        binding.viewHead.tvTitle.text = "账号安全"
        binding.viewHead.ivBack.setOnClickListener {
            finish()
        }

        binding.stv1.setOnClickListener(this)
        binding.stv2.setOnClickListener(this)
        binding.stv3.setOnClickListener(this)
        binding.stv4.setOnClickListener(this)
        binding.stv5.setOnClickListener(this)
    }

    override fun showLoading() {
    }

    override fun hideLoading() {
    }

    override fun onClick(p0: View?) {
        when (p0!!.id) {
            R.id.stv_1 -> startActivity(
                Intent(this, ReflectAccountActivity::class.java)
            )
            R.id.stv_2 -> startActivity(Intent(this, PayPasswordActivity::class.java))
            R.id.stv_3 -> startActivity(Intent(this, ChangeBindPhoneActivity::class.java))
            R.id.stv_4 -> {
                val builder = AlertDialog.Builder(this)
                builder.setMessage("您正在操作账号注销,一旦点击确认注销，本账号资料将从平台销毁，不可恢复，请确认是否注销？")
                builder.setTitle("注销")
                builder.setPositiveButton("确定") { dialog, _ ->
                    dialog.dismiss()
                }
                builder.setNegativeButton("取消") { dialog, _ -> dialog.dismiss() }
                builder.create().show()
            }
            R.id.stv_5 -> startActivity(Intent(this, VerifiedActivity::class.java))
        }
    }
}