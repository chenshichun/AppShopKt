package com.app.shop.ui.contract

import com.app.shop.base.BaseContract
import com.app.shop.bean.AliPayDataBean
import com.app.shop.bean.BaseNetModel
import com.app.shop.bean.PointInfoBean
import com.app.shop.req.BalancePayReq
import com.app.shop.req.ZFBPayReq

/**
 * @author chenshichun
 * 创建日期：2022/8/1
 * 描述：
 *
 */
interface PayOrderContract : BaseContract {
    interface View : BaseContract.BaseView {
        fun aliPay(mData: BaseNetModel<AliPayDataBean>)
        fun payBalance(mData: BaseNetModel<Any>)
        fun pointInfo(mData: BaseNetModel<PointInfoBean>)
    }

    interface Presenter {
        fun aliPay(zfbPayReq: ZFBPayReq)
        fun payBalance(balancePayReq: BalancePayReq)
        fun pointInfo()
    }
}