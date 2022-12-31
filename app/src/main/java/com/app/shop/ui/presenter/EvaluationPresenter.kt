package com.app.shop.ui.presenter

import com.app.shop.base.BasePresenter
import com.app.shop.manager.Constants
import com.app.shop.req.CommentReq
import com.app.shop.retrofit.ApiRequest
import com.app.shop.service.HomeService
import com.app.shop.ui.contract.EvaluationContract
import com.app.shop.util.ToastUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody

/**
 * @author chenshichun
 * 创建日期：2022/8/25
 * 描述：
 */
class EvaluationPresenter : BasePresenter<EvaluationContract.View>(), EvaluationContract.Presenter {
    override fun upload(file: MultipartBody.Part, description: RequestBody) {
        CoroutineScope(Dispatchers.Main).launch {
            mView!!.showLoading()
            val response = ApiRequest.create(HomeService::class.java).upload(file, description)
            mView!!.hideLoading()
            if (response.body() == null) {
                ToastUtil.showNoNetworkToast()
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

    override fun commentSave(commentReq: CommentReq) {
        CoroutineScope(Dispatchers.Main).launch {
            mView!!.showLoading()
            val response = ApiRequest.create(HomeService::class.java).commentSave(commentReq)
            mView!!.hideLoading()
            if (response.body() == null) {
                ToastUtil.showNoNetworkToast()
            } else {
                if (response.body()!!.code == Constants.WEB_RESP_CODE_SUCCESS) {
                    mView!!.commentSave(response.body()!!)
                    ToastUtil.showToast(response.body()!!.msg.toString())
                } else {
                    ToastUtil.showToast(response.body()!!.msg.toString())
                }
            }
        }
    }
}