package com.app.shop.ui.contract

import com.app.shop.base.BaseContract
import com.app.shop.bean.BaseNetModel
import com.app.shop.req.SetPwdReq
import com.app.shop.req.SmsSendReq
import retrofit2.http.Body

/**
 * @author chenshichun
 * 创建日期：2022/7/13
 * 描述：
 *
 */
interface ForgetPasswordContract : BaseContract {
    interface View : BaseContract.BaseView {
        fun smsCode(mData: BaseNetModel<Any>)
        fun setpwd(mData: BaseNetModel<Any>)
    }

    interface Presenter {
        fun smsCode(smsSendReq: SmsSendReq)
        fun setpwd(setPwdReq: SetPwdReq)
    }
}