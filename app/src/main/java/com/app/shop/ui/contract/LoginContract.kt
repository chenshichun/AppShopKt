package com.app.shop.ui.contract

import com.app.shop.base.BaseContract
import com.app.shop.bean.BaseNetModel
import com.app.shop.bean.UserDataBean
import com.app.shop.req.SmsLoginReq
import com.app.shop.req.SmsSendReq
import com.app.shop.req.WxLoginReq
import retrofit2.http.Body

/**
 * @author chenshichun
 * 创建日期：2022/7/13
 * 描述：
 *
 */
interface LoginContract {
    interface View : BaseContract.BaseView {
        fun smsCode(mData: BaseNetModel<Any>)
        fun smsLogin(mData: BaseNetModel<UserDataBean>)
        fun wechatLogin(mData: BaseNetModel<UserDataBean>)
        fun bindPhone()
    }

    interface Presenter {
        fun smsCode(smsSendReq: SmsSendReq)
        fun smsLogin(smsReq: SmsLoginReq)
        fun wechatLogin(wxLoginReq: WxLoginReq)
    }
}