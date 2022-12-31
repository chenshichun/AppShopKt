package com.app.shop.ui.contract

import com.app.shop.base.BaseContract
import com.app.shop.bean.BaseNetModel
import com.app.shop.req.CashReq

/**
 * @author chenshichun
 * 创建日期：2022/8/22
 * 描述：
 *
 */
interface WithdrawContract : BaseContract {
    interface View : BaseContract.BaseView {
        fun cashout(mData: BaseNetModel<Any>)
    }

    interface Presenter {
        fun cashout(cashReq: CashReq)
    }
}