package com.app.shop.ui.presenter

import com.app.shop.base.BasePresenter
import com.app.shop.manager.Constants
import com.app.shop.req.TransferPointrReq
import com.app.shop.retrofit.ApiRequest
import com.app.shop.service.HomeService
import com.app.shop.ui.contract.ConversionIntegralContract
import com.app.shop.util.ToastUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * @author chenshichun
 * 创建日期：2022/9/28
 * 描述：
 */
class ConversionIntegralPresenter : BasePresenter<ConversionIntegralContract.View>(),
    ConversionIntegralContract.Presenter {
    override fun transferPoint(transferPointrReq: TransferPointrReq) {
        CoroutineScope(Dispatchers.Main).launch {
            mView!!.showLoading()
            val response =
                ApiRequest.create(HomeService::class.java).transferPoint(transferPointrReq)
            mView!!.hideLoading()
            if (response.body() == null) {
                ToastUtil.showNoNetworkToast()
            } else {
                if (response.body()!!.code == Constants.WEB_RESP_CODE_SUCCESS) {
                    mView!!.transferPoint(response.body()!!)
                    ToastUtil.showToast(response.body()!!.msg.toString())
                } else {
                    ToastUtil.showToast(response.body()!!.msg.toString())
                }
            }
        }
    }

    override fun pointInfo() {
        CoroutineScope(Dispatchers.Main).launch {
            val response = ApiRequest.create(HomeService::class.java).pointInfo()
            if (response.body() == null) {
                ToastUtil.showNoNetworkToast()
            } else {
                if (response.body()!!.code == Constants.WEB_RESP_CODE_SUCCESS) {
                    mView!!.pointInfo(response.body()!!)
                } else {
                    ToastUtil.showToast(response.body()!!.msg.toString())
                }
            }
        }
    }

}