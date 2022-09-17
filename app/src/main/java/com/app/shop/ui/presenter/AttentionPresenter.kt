package com.app.shop.ui.presenter

import com.app.shop.base.BasePresenter
import com.app.shop.manager.Constants
import com.app.shop.retrofit.ApiRequest
import com.app.shop.service.HomeService
import com.app.shop.service.LoginService
import com.app.shop.ui.contract.AttentionContract
import com.app.shop.util.ToastUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * @author chenshichun
 * 创建日期：2022/7/18
 * 描述：
 *
 */
class AttentionPresenter : BasePresenter<AttentionContract.View>(), AttentionContract.Presenter {
    override fun storeList() {
        CoroutineScope(Dispatchers.Main).launch {
            mView!!.showLoading()
            val response = ApiRequest.create(HomeService::class.java).storeList()
            mView!!.hideLoading()
            if (response.body() == null) {
                ToastUtil.showNoNetworkToast()
                mView!!.noNetworkView()
            } else {
                if (response.body()!!.code == Constants.WEB_RESP_CODE_SUCCESS) {
                    mView!!.storeList(response.body()!!)
                } else {
                    ToastUtil.showToast(response.body()!!.msg.toString())
                    mView!!.showError()
                }
            }
        }
    }
}