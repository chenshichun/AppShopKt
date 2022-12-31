package com.app.shop.ui.contract

import com.app.shop.base.BaseContract
import com.app.shop.bean.*
import com.app.shop.req.*

/**
 * @author chenshichun
 * 创建日期：2022/10/11
 * 描述：
 */
class PayCartOrderContract : BaseContract {
    interface View : BaseContract.BaseView {
        fun aliPay(mData: BaseNetModel<AliPayDataBean>)
        fun payBalance(mData: BaseNetModel<Any>)
        fun pointInfo(mData: BaseNetModel<PointInfoBean>)
        fun calcMultipleOrder(mData: BaseNetModel<CalcDirectBean>)
        fun orderFilter(mData: BaseNetModel<OrderFilterBean>)
    }

    interface Presenter {
        fun aliPay(zfbPayReq: ZFBPayReq)
        fun payBalance(balancePayReq: BalancePayReq)
        fun pointInfo()
        fun calcMultipleOrder(order_ids: OrderIdsReq)
        fun orderFilter(order_ids: String)
    }
}