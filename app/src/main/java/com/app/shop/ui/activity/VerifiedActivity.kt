package com.app.shop.ui.activity

import com.app.shop.R
import com.app.shop.base.BaseActivity
import com.app.shop.bean.BaseNetModel
import com.app.shop.databinding.ActivityVerifiedBinding
import com.app.shop.manager.AccountManager
import com.app.shop.req.CertReq
import com.app.shop.ui.contract.VerifiedContract
import com.app.shop.ui.presenter.VerifiedPresenter
import com.app.shop.util.ToastUtil
import com.gyf.immersionbar.ktx.immersionBar

/**
 * @author chenshichun
 * 创建日期：2022/7/4
 * 描述：实名认证
 */
class VerifiedActivity : BaseActivity<ActivityVerifiedBinding, VerifiedPresenter>(),
    VerifiedContract.View {
    override fun getPresenter(): VerifiedPresenter {
        return VerifiedPresenter()
    }

    override fun initView() {
        immersionBar {
            statusBarColor(R.color.white)
            statusBarDarkFont(true)
        }

        binding.viewHead.tvTitle.text = "实名认证"
        binding.viewHead.ivBack.setOnClickListener {
            finish()
        }

        binding.btConfirm.setOnClickListener {
            if (binding.etName.text.toString().isEmpty()) {
                ToastUtil.showToast("请输入姓名")
                return@setOnClickListener
            }
            if (binding.etSfz.text.toString().isEmpty()) {
                ToastUtil.showToast("请输入身份证号码")
                return@setOnClickListener
            }
            var certReq = CertReq(
                binding.etName.text.toString(),
                binding.etSfz.text.toString(),
                AccountManager.getAccountInfo()!!.phone.toString()
            )
            mPresenter!!.cert(certReq)
        }
    }

    override fun cert(mData: BaseNetModel<Any>) {
        ToastUtil.showToast(mData.msg)
        finish()
    }

    override fun showLoading() {
        showLoadingDialog()
    }

    override fun hideLoading() {
        closeLoadingDialog()
    }
}