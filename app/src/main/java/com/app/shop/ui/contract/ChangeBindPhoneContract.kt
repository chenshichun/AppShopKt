package com.app.shop.ui.contract

import com.app.shop.base.BaseContract
import com.app.shop.bean.BaseNetModel
import com.app.shop.req.SmsSendReq

/**
 * @author chenshichun
 * 创建日期：2022/7/25
 * 描述：
 *
 */
interface ChangeBindPhoneContract : BaseContract {
    interface View : BaseContract.BaseView {
        fun smsCode(mData: BaseNetModel<Any>)
    }

    interface Presenter {
        fun smsCode(smsSendReq: SmsSendReq)
    }
}