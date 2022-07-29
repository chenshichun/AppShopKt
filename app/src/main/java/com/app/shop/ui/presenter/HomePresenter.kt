package com.app.shop.ui.presenter

import com.app.shop.base.BasePresenter
import com.app.shop.manager.Constants
import com.app.shop.retrofit.ApiRequest
import com.app.shop.service.HomeService
import com.app.shop.ui.contract.HomeContract
import com.app.shop.util.ToastUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * @author chenshichun
 * 创建日期：2022/7/4
 * 描述：
 *
 */
class HomePresenter : BasePresenter<HomeContract.View>(), HomeContract.Presenter {
    override fun getBannerList() {
        CoroutineScope(Dispatchers.Main).launch {
            val response = ApiRequest.create(HomeService::class.java).getBannerList()
            if (response.body() == null) {
                ToastUtil.showNoNetworkToast()
            } else {
                if (response.body()!!.code == Constants.WEB_RESP_CODE_SUCCESS) {
                    mView!!.getBannerList(response.body()!!)
                } else {
                    ToastUtil.showToast(response.body()!!.msg.toString())
                }
            }
        }
    }


    override fun getProdHomeData(page: Int, size: Int) {
        CoroutineScope(Dispatchers.Main).launch {
            val response = ApiRequest.create(HomeService::class.java).getProdHomeData(page, size)
            if (response.body() == null) {
                ToastUtil.showNoNetworkToast()
                mView!!.noNetworkView()
            } else {
                if (response.body()!!.code == Constants.WEB_RESP_CODE_SUCCESS) {
                    mView!!.getProdHomeData(response.body()!!)
                } else {
                    mView!!.showError()
                    ToastUtil.showToast(response.body()!!.msg.toString())
                }
            }
        }
    }

    override fun sign() {
        CoroutineScope(Dispatchers.Main).launch {
            val response = ApiRequest.create(HomeService::class.java).sign()
            if (response.body() == null) {
                ToastUtil.showNoNetworkToast()
            } else {
                if (response.body()!!.code == Constants.WEB_RESP_CODE_SUCCESS) {
                    mView!!.sign(response.body()!!)
                } else {
                    ToastUtil.showToast(response.body()!!.msg.toString())
                }
            }
        }
    }
}