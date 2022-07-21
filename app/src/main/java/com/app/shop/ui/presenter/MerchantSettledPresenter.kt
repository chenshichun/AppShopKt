package com.app.shop.ui.presenter

import com.app.shop.base.BasePresenter
import com.app.shop.manager.Constants
import com.app.shop.req.MerchantSettledReq
import com.app.shop.retrofit.ApiRequest
import com.app.shop.service.HomeService
import com.app.shop.ui.contract.MerchantSettledContract
import com.app.shop.util.ToastUtil
import com.orhanobut.logger.Logger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody

/**
 * @author chenshichun
 * 创建日期：2022/7/19
 * 描述：
 *
 */
class MerchantSettledPresenter : BasePresenter<MerchantSettledContract.View>(),
    MerchantSettledContract.Presenter {
    override fun upload(file: MultipartBody.Part,description: RequestBody) {
        CoroutineScope(Dispatchers.Main).launch {
            val response = ApiRequest.create(HomeService::class.java).upload(file, description)
            if (response.body() == null) {
                ToastUtil.showNoIntentToast()
            } else {
                if (response.body()!!.code == Constants.WEB_RESP_CODE_SUCCESS) {
                    mView!!.upload(response.body()!!)
                    ToastUtil.showToast(response.body()!!.msg.toString())
                } else {
                    ToastUtil.showToast(response.body()!!.msg.toString())
                }
            }
        }
    }

    override fun merchantApply(merchantSettledReq: MerchantSettledReq) {
        CoroutineScope(Dispatchers.Main).launch {
            val response =
                ApiRequest.create(HomeService::class.java).merchantApply(merchantSettledReq)
            if (response.body() == null) {
                ToastUtil.showNoIntentToast()
            } else {
                if (response.body()!!.code == Constants.WEB_RESP_CODE_SUCCESS) {
                    mView!!.merchantApply(response.body()!!)
                    ToastUtil.showToast(response.body()!!.msg.toString())
                } else {
                    ToastUtil.showToast(response.body()!!.msg.toString())
                }
            }
        }
    }
}