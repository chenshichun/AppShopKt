package com.app.shop.ui.presenter

import com.app.shop.base.BasePresenter
import com.app.shop.manager.Constants
import com.app.shop.req.AddrIdReq
import com.app.shop.retrofit.ApiRequest
import com.app.shop.service.HomeService
import com.app.shop.ui.contract.AddressListContract
import com.app.shop.util.ToastUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * @author chenshichun
 * 创建日期：2022/7/27
 * 描述：
 *
 */
class AddressListPresenter : BasePresenter<AddressListContract.View>(),
    AddressListContract.Presenter {

    override fun addrList() {
        CoroutineScope(Dispatchers.Main).launch {
            val response = ApiRequest.create(HomeService::class.java).addrList()
            if (response.body() == null) {
            } else {
                if (response.body()!!.code == Constants.WEB_RESP_CODE_SUCCESS) {
                    mView!!.addrList(response.body()!!)
                } else {
                    ToastUtil.showToast(response.body()!!.msg.toString())
                }
            }
        }
    }

    override fun addrDelete(addrIdReq: AddrIdReq) {
        CoroutineScope(Dispatchers.Main).launch {
            val response = ApiRequest.create(HomeService::class.java).addrDel(addrIdReq)
            if (response.body() == null) {
            } else {
                if (response.body()!!.code == Constants.WEB_RESP_CODE_SUCCESS) {
                    mView!!.addrDelete(response.body()!!)
                } else {
                    ToastUtil.showToast(response.body()!!.msg.toString())
                }
            }
        }
    }

    override fun addrDefultSet(addrIdReq: AddrIdReq) {
        CoroutineScope(Dispatchers.Main).launch {
            val response = ApiRequest.create(HomeService::class.java).addrDefultSet(addrIdReq)
            if (response.body() == null) {
            } else {
                if (response.body()!!.code == Constants.WEB_RESP_CODE_SUCCESS) {
                    mView!!.addrDefultSet(response.body()!!)
                } else {
                    ToastUtil.showToast(response.body()!!.msg.toString())
                }
            }
        }
    }
}