package com.app.shop.ui.presenter

import android.provider.SyncStateContract
import com.app.shop.base.BasePresenter
import com.app.shop.manager.Constants
import com.app.shop.req.RegisterReq
import com.app.shop.retrofit.ApiRequest
import com.app.shop.service.LoginService
import com.app.shop.ui.contract.RegisterContract
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
class RegisterPresenter : BasePresenter<RegisterContract.View>(), RegisterContract.Presenter {

    override fun smsRegister(registerReq: RegisterReq) {
        CoroutineScope(Dispatchers.Main).launch {
            val response = ApiRequest.create(LoginService::class.java).smsRegister(registerReq)
            if (response.body() == null) {
                ToastUtil.showNoIntentToast()
            } else {
                if (response.body()!!.code == Constants.WEB_RESP_CODE_SUCCESS) {
                    mView!!.smsRegister(response.body()!!)
                } else {
                    ToastUtil.showToast(response.body()!!.msg.toString())
                }
            }
        }
    }

}