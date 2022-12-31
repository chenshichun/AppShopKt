package com.app.shop.ui.contract

import com.app.shop.base.BaseContract
import com.app.shop.bean.BaseNetModel
import com.app.shop.req.RegisterReq
import com.app.shop.req.SmsSendReq
import retrofit2.http.Body

/**
 * @author chenshichun
 * 创建日期：2022/7/13
 * 描述：
 *
 */
interface RegisterContract : BaseContract {
    interface View : BaseContract.BaseView {
        fun smsCode(mData: BaseNetModel<Any>)
        fun smsRegister(mData: BaseNetModel<Any>)
    }

    interface Presenter {
        fun smsCode(smsSendReq: SmsSendReq)
        fun smsRegister(registerReq: RegisterReq)
    }
}