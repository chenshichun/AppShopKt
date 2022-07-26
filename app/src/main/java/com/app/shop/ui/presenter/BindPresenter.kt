package com.app.shop.ui.presenter

import com.app.shop.base.BasePresenter
import com.app.shop.manager.Constants
import com.app.shop.req.BindWechatReq
import com.app.shop.req.SmsSendReq
import com.app.shop.retrofit.ApiRequest
import com.app.shop.service.LoginService
import com.app.shop.ui.contract.BindContract
import com.app.shop.util.ToastUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * @author chenshichun
 * 创建日期：2022/7/23
 * 描述：
 *
 */
class BindPresenter : BasePresenter<BindContract.View>(), BindContract.Presenter {
    override fun smsCode(smsSendReq: SmsSendReq) {
        CoroutineScope(Dispatchers.Main).launch {
            mView!!.showLoading()
            val response = ApiRequest.create(LoginService::class.java).smsCode(smsSendReq)
            mView!!.hideLoading()
            if (response.body() == null) {
                ToastUtil.showNoIntentToast()
            } else {
                if (response.body()!!.code == Constants.WEB_RESP_CODE_SUCCESS) {
                    mView!!.smsCode(response.body()!!)
                } else {
                    ToastUtil.showToast(response.body()!!.msg.toString())
                }
            }
        }
    }

    override fun bindWechat(bindWechatReq: BindWechatReq) {
        CoroutineScope(Dispatchers.Main).launch {
            mView!!.showLoading()
            val response = ApiRequest.create(LoginService::class.java).bindWechat(bindWechatReq)
            mView!!.hideLoading()
            if (response.body() == null) {
                ToastUtil.showNoIntentToast()
            } else {
                if (response.body()!!.code == Constants.WEB_RESP_CODE_SUCCESS) {
                    mView!!.bindWechat(response.body()!!)
                } else {
                    ToastUtil.showToast(response.body()!!.msg.toString())
                }
            }
        }
    }
}