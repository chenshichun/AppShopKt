package com.app.shop.ui.contract

import com.app.shop.base.BaseContract
import com.app.shop.bean.BaseNetModel
import com.app.shop.bean.UserDataBean
import com.app.shop.req.SmsReq

/**
 * @author chenshichun
 * 创建日期：2022/7/13
 * 描述：
 *
 */
interface LoginContract {
    interface View : BaseContract.BaseView {
        fun smsLogin(mData: BaseNetModel<UserDataBean>)
    }

    interface Presenter {
        fun smsLogin(smsReq: SmsReq)
    }
}