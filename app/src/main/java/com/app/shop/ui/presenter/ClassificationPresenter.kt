package com.app.shop.ui.presenter

import com.app.shop.base.BasePresenter
import com.app.shop.manager.Constants
import com.app.shop.retrofit.ApiRequest
import com.app.shop.service.HomeService
import com.app.shop.ui.contract.ClassificationContract
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
class ClassificationPresenter : BasePresenter<ClassificationContract.View>(),
    ClassificationContract.Presenter {

    override fun getCateList() {
        CoroutineScope(Dispatchers.Main).launch {
            val response = ApiRequest.create(HomeService::class.java).getCateList()
            if (response.body() == null) {
                ToastUtil.showNoNetworkToast()
                mView!!.noNetworkView()
            } else {
                if (response.body()!!.code == Constants.WEB_RESP_CODE_SUCCESS) {
                    mView!!.getCateList(response.body()!!)
                } else {
                    mView!!.showError()
                    ToastUtil.showToast(response.body()!!.msg.toString())
                }
            }
        }
    }
}