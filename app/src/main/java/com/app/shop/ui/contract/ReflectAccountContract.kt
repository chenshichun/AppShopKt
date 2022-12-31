package com.app.shop.ui.contract

import com.app.shop.base.BaseContract
import com.app.shop.bean.BaseNetModel
import com.app.shop.req.SmsSendReq
import com.app.shop.req.WalletSetReq

/**
 * @author chenshichun
 * 创建日期：2022/7/25
 * 描述：
 *
 */
interface ReflectAccountContract : BaseContract {
    interface View : BaseContract.BaseView {
        fun smsCode(mData: BaseNetModel<Any>)
        fun walletSet(mData: BaseNetModel<Any>)
    }

    interface Presenter {
        fun smsCode(smsSendReq: SmsSendReq)
        fun walletSet(walletSetReq: WalletSetReq)
    }
}