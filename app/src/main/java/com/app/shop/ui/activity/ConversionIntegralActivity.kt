package com.app.shop.ui.activity

import com.app.shop.R
import com.app.shop.base.BaseActivity
import com.app.shop.bean.BaseNetModel
import com.app.shop.bean.IntegralBean
import com.app.shop.databinding.ActivityConversionIntegralBinding
import com.app.shop.manager.Constants
import com.app.shop.req.TransferPointrReq
import com.app.shop.ui.contract.ConversionIntegralContract
import com.app.shop.ui.presenter.ConversionIntegralPresenter
import com.app.shop.util.JsonUtils
import com.gyf.immersionbar.ktx.immersionBar
import com.orhanobut.logger.Logger

/*
* 扫码转积分
* */
class ConversionIntegralActivity :
    BaseActivity<ActivityConversionIntegralBinding, ConversionIntegralPresenter>(),
    ConversionIntegralContract.View {

    private var result: String = ""

    override fun getPresenter(): ConversionIntegralPresenter {
        return ConversionIntegralPresenter()
    }

    override fun initView() {
        immersionBar {
            statusBarColor(R.color.white)
            statusBarDarkFont(true)
        }

        binding.viewHead.tvTitle.text = "转积分"
        binding.viewHead.ivBack.setOnClickListener {
            finish()
        }

        result = intent.getStringExtra(Constants.CODE_QR).toString()
        Logger.d(result)
        val integralBean: IntegralBean = JsonUtils.jsonToBean(result, IntegralBean::class.java)
        binding.etPhone.setText(integralBean.phone)

        binding.btConfirm.setOnClickListener {
            val transferPointrReq = TransferPointrReq(
                integralBean.phone.toString(),
                binding.etIntergral.text.toString(),
                binding.etMark.text.toString()
            )
            mPresenter!!.transferPoint(transferPointrReq)
        }
    }

    override fun transferPoint(mData: BaseNetModel<Any>) {
        finish()
    }

    override fun showLoading() {
        showLoadingDialog()
    }

    override fun hideLoading() {
        closeLoadingDialog()
    }
}