package com.app.shop.ui.activity

import com.app.shop.R
import com.app.shop.base.BaseActivity
import com.app.shop.bean.BaseNetModel
import com.app.shop.bean.IntegralBean
import com.app.shop.bean.PointInfoBean
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
            var point_type = ""
            if (binding.rb1.isChecked) {
                point_type = "reward"
            } else if (binding.rb2.isChecked) {
                point_type = "barter"
            } else if (binding.rb3.isChecked) {
                point_type = "expend"
            }
            val transferPointrReq = TransferPointrReq(
                integralBean.phone.toString(),
                binding.etIntergral.text.toString(),
                binding.etMark.text.toString(),
                point_type
            )
            mPresenter!!.transferPoint(transferPointrReq)
        }
    }

    override fun onResume() {
        mPresenter!!.pointInfo()
        super.onResume()
    }

    override fun transferPoint(mData: BaseNetModel<Any>) {
        finish()
    }

    override fun pointInfo(mData: BaseNetModel<PointInfoBean>) {
        binding.rb1.text = String.format(
            getString(R.string.qdjf),
            mData.data!!.point.reward,
            (mData.data!!.point.trans_reward_rank.toDouble() * 100).toString()
        )
        binding.rb2.text = String.format(
            getString(R.string.yhjf),
            mData.data!!.point.barter,
            (mData.data!!.point.trans_barter_rank.toDouble() * 100).toString()
        )
        binding.rb3.text = String.format(
            getString(R.string.xfjf),
            mData.data!!.point.expend,
            (mData.data!!.point.trans_expend_rank.toDouble() * 100).toString()
        )
    }

    override fun showLoading() {
        showLoadingDialog()
    }

    override fun hideLoading() {
        closeLoadingDialog()
    }
}