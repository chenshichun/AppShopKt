package com.app.shop.ui.presenter

import com.app.shop.base.BasePresenter
import com.app.shop.manager.Constants
import com.app.shop.req.BalancePayReq
import com.app.shop.req.OrderIdsReq
import com.app.shop.req.ZFBPayReq
import com.app.shop.retrofit.ApiRequest
import com.app.shop.service.HomeService
import com.app.shop.ui.contract.PayCartOrderContract
import com.app.shop.util.ToastUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * @author chenshichun
 * 创建日期：2022/10/11
 * 描述：
 */
class PayCartOrderPresenter : BasePresenter<PayCartOrderContract.View>(),
    PayCartOrderContract.Presenter {

    override fun aliPay(zfbPayReq: ZFBPayReq) {
        CoroutineScope(Dispatchers.Main).launch {
            val response = ApiRequest.create(HomeService::class.java).aliPay(zfbPayReq)
            if (response.body() == null) {
                ToastUtil.showNoNetworkToast()
            } else {
                if (response.body()!!.code == Constants.WEB_RESP_CODE_SUCCESS) {
                    mView!!.aliPay(response.body()!!)
                } else {
                    ToastUtil.showToast(response.body()!!.msg.toString())
                }
            }
        }
    }

    override fun payBalance(balancePayReq: BalancePayReq) {
        CoroutineScope(Dispatchers.Main).launch {
            val response =
                ApiRequest.create(HomeService::class.java).payBalance(balancePayReq)
            if (response.body() == null) {
                ToastUtil.showNoNetworkToast()
            } else {
                if (response.body()!!.code == Constants.WEB_RESP_CODE_SUCCESS) {
                    mView!!.payBalance(response.body()!!)
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

    override fun calcMultipleOrder(order_ids: OrderIdsReq) {
        CoroutineScope(Dispatchers.Main).launch {
            val response = ApiRequest.create(HomeService::class.java).calcMultipleOrder(order_ids)
            if (response.body() == null) {
                ToastUtil.showNoNetworkToast()
            } else {
                if (response.body()!!.code == Constants.WEB_RESP_CODE_SUCCESS) {
                    mView!!.calcMultipleOrder(response.body()!!)
                } else {
                    ToastUtil.showToast(response.body()!!.msg.toString())
                }
            }
        }
    }

    override fun orderFilter(order_ids: String) {
        CoroutineScope(Dispatchers.Main).launch {
            val response = ApiRequest.create(HomeService::class.java).orderFilter(order_ids)
            if (response.body() == null) {
                ToastUtil.showNoNetworkToast()
            } else {
                if (response.body()!!.code == Constants.WEB_RESP_CODE_SUCCESS) {
                    mView!!.orderFilter(response.body()!!)
                } else {
                    ToastUtil.showToast(response.body()!!.msg.toString())
                }
            }
        }
    }
}