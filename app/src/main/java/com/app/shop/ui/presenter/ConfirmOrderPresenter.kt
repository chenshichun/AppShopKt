package com.app.shop.ui.presenter

import com.app.shop.base.BasePresenter
import com.app.shop.manager.Constants
import com.app.shop.req.CreateOrderReq
import com.app.shop.retrofit.ApiRequest
import com.app.shop.service.HomeService
import com.app.shop.ui.contract.ConfirmOrderContract
import com.app.shop.util.ToastUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * @author chenshichun
 * 创建日期：2022/8/1
 * 描述：
 *
 */
class ConfirmOrderPresenter : BasePresenter<ConfirmOrderContract.View>(),
    ConfirmOrderContract.Presenter {

    override fun addrDefault() {
        CoroutineScope(Dispatchers.Main).launch {
            mView!!.showLoading()
            val response = ApiRequest.create(HomeService::class.java).addrDefault()
            mView!!.hideLoading()
            if (response.body() == null) {
                ToastUtil.showNoNetworkToast()
            } else {
                if (response.body()!!.code == Constants.WEB_RESP_CODE_SUCCESS) {
                    mView!!.addrDefault(response.body()!!)
                } else {
                    ToastUtil.showToast(response.body()!!.msg.toString())
                }
            }
        }
    }

    override fun orderSubmit(createOrderReq: CreateOrderReq) {
        CoroutineScope(Dispatchers.Main).launch {
            mView!!.showLoading()
            val response = ApiRequest.create(HomeService::class.java).orderSubmit(createOrderReq)
            mView!!.hideLoading()
            if (response.body() == null) {
                ToastUtil.showNoNetworkToast()
            } else {
                if (response.body()!!.code == Constants.WEB_RESP_CODE_SUCCESS) {
                    mView!!.orderSubmit(response.body()!!)
                } else {
                    ToastUtil.showToast(response.body()!!.msg.toString())
                }
            }
        }
    }
}