package com.app.shop.ui.presenter

import com.app.shop.base.BasePresenter
import com.app.shop.manager.Constants
import com.app.shop.retrofit.ApiRequest
import com.app.shop.service.HomeService
import com.app.shop.ui.contract.CategoryListContract
import com.app.shop.util.ToastUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * @author chenshichun
 * 创建日期：2022/7/28
 * 描述：
 *
 */
class CategoryListPresenter : BasePresenter<CategoryListContract.View>(),
    CategoryListContract.Presenter {

    override fun getCateByIdData(page: Int, size: Int, cateId: String, sort: String) {
        CoroutineScope(Dispatchers.Main).launch {
            val response =
                ApiRequest.create(HomeService::class.java).getCateByIdData(page, size, cateId, sort)
            if (response.body() == null) {
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

    override fun getProdFeaturedData(page: Int, size: Int, sort: String) {
        CoroutineScope(Dispatchers.Main).launch {
            val response =
                ApiRequest.create(HomeService::class.java).getProdFeaturedData(page, size, sort)
            if (response.body() == null) {
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

    override fun getProdRecommendData(page: Int, size: Int, sort: String) {
        CoroutineScope(Dispatchers.Main).launch {
            val response =
                ApiRequest.create(HomeService::class.java).getProdRecommendData(page, size, sort)
            if (response.body() == null) {
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
}