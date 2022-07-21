package com.app.shop.ui.presenter

import com.app.shop.base.BasePresenter
import com.app.shop.manager.Constants
import com.app.shop.req.SmsLoginReq
import com.app.shop.req.SmsSendReq
import com.app.shop.retrofit.ApiRequest
import com.app.shop.service.LoginService
import com.app.shop.ui.contract.LoginContract
import com.app.shop.util.ToastUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * @author chenshichun
 * 创建日期：2022/7/13
 * 描述：
 *
 */
class LoginPresent : BasePresenter<LoginContract.View>(), LoginContract.Presenter {

    override fun smsCode(smsSendReq: SmsSendReq) {
        CoroutineScope(Dispatchers.Main).launch {
            val response = ApiRequest.create(LoginService::class.java).smsCode(smsSendReq)
            if (response.body() == null) {
                ToastUtil.showNoIntentToast()
            } else {
                if (response.body()!!.code == Constants.WEB_RESP_CODE_SUCCESS) {
                    mView!!.smsCode(response.body()!!)
                    ToastUtil.showToast(response.body()!!.msg.toString())
                } else {
                    ToastUtil.showToast(response.body()!!.msg.toString())
                }
            }
        }
    }

    override fun smsLogin(smsReq: SmsLoginReq) {
        CoroutineScope(Dispatchers.Main).launch {
            val response = ApiRequest.create(LoginService::class.java).smsLogin(smsReq)
            if (response.body() == null) {
                ToastUtil.showNoIntentToast()
            } else {
                if (response.body()!!.code == Constants.WEB_RESP_CODE_SUCCESS) {
                    mView!!.smsLogin(response.body()!!)
                } else {
                    ToastUtil.showToast(response.body()!!.msg.toString())
                }
            }
        }
    }
}