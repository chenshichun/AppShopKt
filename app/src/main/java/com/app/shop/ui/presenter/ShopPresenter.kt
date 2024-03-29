package com.app.shop.ui.presenter

import com.app.shop.base.BasePresenter
import com.app.shop.manager.Constants
import com.app.shop.retrofit.ApiRequest
import com.app.shop.service.HomeService
import com.app.shop.ui.contract.ShopContract
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
class ShopPresenter : BasePresenter<ShopContract.View>(),
    ShopContract.Presenter {
    override fun storeLocalList(
        page: Int,
        size: Int,
        dist: Int,
        search_name: String
    ) {
        CoroutineScope(Dispatchers.Main).launch {
            mView!!.showLoading()
            val response = ApiRequest.create(HomeService::class.java).storeLocalList(page, size, dist, search_name)
            mView!!.hideLoading()
            if (response.body() == null) {
                ToastUtil.showNoNetworkToast()
            } else {
                if (response.body()!!.code == Constants.WEB_RESP_CODE_SUCCESS) {
                    mView!!.storeLocalList(response.body()!!)
                } else {
                    ToastUtil.showToast(response.body()!!.msg.toString())
                }
            }
        }
    }
}