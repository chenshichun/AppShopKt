package com.app.shop.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.app.shop.R
import com.app.shop.base.BaseActivity
import com.app.shop.databinding.ActivityAboutUsBinding
import com.app.shop.manager.Constants
import com.app.shop.ui.contract.AboutUsContract
import com.app.shop.ui.presenter.AboutUsPresenter
import com.app.shop.util.IntentUtil
import com.gyf.immersionbar.ktx.immersionBar

/**
 * @author chenshichun
 * 创建日期：2022/7/25
 * 描述：关于我们
 *
 */
class AboutUsActivity : BaseActivity<ActivityAboutUsBinding, AboutUsPresenter>(),
    AboutUsContract.View, View.OnClickListener {
    override fun getPresenter(): AboutUsPresenter {
        return AboutUsPresenter()
    }

    override fun initView() {
        immersionBar {
            statusBarColor(R.color.white)
            statusBarDarkFont(true)
        }

        binding.viewHead.tvTitle.text = "关于我们"
        binding.viewHead.ivBack.setOnClickListener {
            finish()
        }

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
        val bundle = Bundle()
        when (p0!!.id) {
            R.id.stv_1 -> {
                bundle.putString(Constants.WEB_TITLE, "用户协议")
                bundle.putString(Constants.WEB_URL, Constants.USET_AGREEMENT_URL)
                IntentUtil.get()!!.goActivity(this, WebViewActivity::class.java, bundle)
            }
            R.id.stv_2 -> {
                bundle.putString(Constants.WEB_TITLE, "隐私政策")
                bundle.putString(Constants.WEB_URL, Constants.PRIVACT_POLICY_URL)
                IntentUtil.get()!!.goActivity(this, WebViewActivity::class.java, bundle)
            }
            R.id.stv_3 -> {}
        }
    }
}