package com.app.shop.ui.presenter

import com.app.shop.base.BasePresenter
import com.app.shop.manager.Constants
import com.app.shop.req.OrderIdReq
import com.app.shop.retrofit.ApiRequest
import com.app.shop.service.HomeService
import com.app.shop.ui.contract.OrderDetailContract
import com.app.shop.util.ToastUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * @author chenshichun
 * 创建日期：2022/9/16
 * 描述：
 */
class OrderDetailPresenter : BasePresenter<OrderDetailContract.View>(),
    OrderDetailContract.Presenter {
    override fun orderDetail(orderId: String) {
        CoroutineScope(Dispatchers.Main).launch {
            mView!!.showLoading()
            val response = ApiRequest.create(HomeService::class.java)
                .orderDetail(orderId)
            mView!!.hideLoading()
            if (response.body() == null) {
                ToastUtil.showNoNetworkToast()
            } else {
                if (response.body()!!.code == Constants.WEB_RESP_CODE_SUCCESS) {
                    mView!!.orderDetail(response.body()!!)
                } else {
                    ToastUtil.showToast(response.body()!!.msg.toString())
                }
            }
        }
    }

    override fun orderCancel(orderIdReq: OrderIdReq) {
        CoroutineScope(Dispatchers.Main).launch {
            mView!!.showLoading()
            val response = ApiRequest.create(HomeService::class.java)
                .orderCancel(orderIdReq)
            mView!!.hideLoading()
            if (response.body() == null) {
                ToastUtil.showNoNetworkToast()
            } else {
                if (response.body()!!.code == Constants.WEB_RESP_CODE_SUCCESS) {
                    mView!!.orderCancel(response.body()!!)
                    ToastUtil.showToast(response.body()!!.msg.toString())
                } else {
                    ToastUtil.showToast(response.body()!!.msg.toString())
                }
            }
        }
    }

    override fun orderConfirm(orderIdReq: OrderIdReq) {
        CoroutineScope(Dispatchers.Main).launch {
            mView!!.showLoading()
            val response = ApiRequest.create(HomeService::class.java)
                .orderConfirm(orderIdReq)
            mView!!.hideLoading()
            if (response.body() == null) {
                ToastUtil.showNoNetworkToast()
            } else {
                if (response.body()!!.code == Constants.WEB_RESP_CODE_SUCCESS) {
                    mView!!.orderConfirm(response.body()!!)
                    ToastUtil.showToast(response.body()!!.msg.toString())
                } else {
                    ToastUtil.showToast(response.body()!!.msg.toString())
                }
            }
        }
    }

    override fun orderDelete(orderIdReq: OrderIdReq) {
        CoroutineScope(Dispatchers.Main).launch {
            mView!!.showLoading()
            val response = ApiRequest.create(HomeService::class.java)
                .orderDelete(orderIdReq)
            mView!!.hideLoading()
            if (response.body() == null) {
                ToastUtil.showNoNetworkToast()
            } else {
                if (response.body()!!.code == Constants.WEB_RESP_CODE_SUCCESS) {
                    mView!!.orderDelete(response.body()!!)
                    ToastUtil.showToast(response.body()!!.msg.toString())
                } else {
                    ToastUtil.showToast(response.body()!!.msg.toString())
                }
            }
        }
    }

}