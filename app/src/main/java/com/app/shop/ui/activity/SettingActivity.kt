package com.app.shop.ui.activity

import android.content.Intent
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.app.shop.R
import com.app.shop.base.BaseActivity
import com.app.shop.databinding.ActivitySettingBinding
import com.app.shop.manager.AccountManager
import com.app.shop.ui.contract.SettingContract
import com.app.shop.ui.presenter.SettingPresenter
import com.gyf.immersionbar.ktx.immersionBar

class SettingActivity : BaseActivity<ActivitySettingBinding,SettingPresenter>(),SettingContract.View ,
    View.OnClickListener{
    override fun getPresenter(): SettingPresenter {
        return SettingPresenter()
    }

    override fun initView() {
        immersionBar {
            statusBarColor(R.color.white)
            statusBarDarkFont(true)
        }

        binding.viewHead.tvTitle.text = "设置"
        binding.viewHead.ivBack.setOnClickListener {
            finish()
        }

        binding.stv1.setOnClickListener(this)
        binding.stv2.setOnClickListener(this)
        binding.stv3.setOnClickListener(this)
        binding.stv4.setOnClickListener(this)
    }

    override fun showLoading() {
        showLoadingDialog()
    }

    override fun hideLoading() {
        closeLoadingDialog()
    }

    override fun onClick(p0: View?) {
        when(p0!!.id){
            R.id.stv_1->{

            }
            R.id.stv_2->{
                Intent(
                    this,
                    AccountSecurityActivity::class.java
                )
            }
            R.id.stv_3->{
                startActivity(Intent(this, AboutUsActivity::class.java))
            }
            R.id.stv_4->{
                val builder =  AlertDialog.Builder(this)
                builder.setMessage("您确定要退出登录吗？")
                builder.setTitle("提示")
                builder.setPositiveButton("确定") { _, _ ->
                    AccountManager.signOut()
                    startActivity(Intent(this, LoginActivity::class.java))
                }
                builder.setNegativeButton("取消") { dialog, _ -> dialog.dismiss() }
                builder.create().show()
            }
        }
    }
}