package com.app.shop.ui.contract

import com.app.shop.base.BaseContract
import com.app.shop.bean.BaseNetModel
import com.app.shop.req.BindWechatReq
import com.app.shop.req.SmsSendReq

/**
 * @author chenshichun
 * 创建日期：2022/7/23
 * 描述：
 */
interface BindContract : BaseContract {
    interface View : BaseContract.BaseView {
        fun smsCode(mData: BaseNetModel<Any>)
        fun bindWechat(mData: BaseNetModel<Any>)
    }

    interface Presenter {
        fun smsCode(smsSendReq: SmsSendReq)
        fun bindWechat(bindWechatReq: BindWechatReq)
    }
}