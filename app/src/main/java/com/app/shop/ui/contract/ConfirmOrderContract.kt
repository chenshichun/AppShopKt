package com.app.shop.ui.contract

import com.app.shop.base.BaseContract
import com.app.shop.bean.BaseNetModel
import com.app.shop.bean.CalcDirectBean
import com.app.shop.bean.DefaultDaarBean
import com.app.shop.bean.OrderIdBean
import com.app.shop.req.CreateOrderReq

/**
 * @author chenshichun
 * 创建日期：2022/8/1
 * 描述：
 *
 */
interface ConfirmOrderContract : BaseContract {
    interface View : BaseContract.BaseView {
        fun addrDefault(mData: BaseNetModel<DefaultDaarBean>)
        fun orderSubmit(mData: BaseNetModel<OrderIdBean>)
        fun calcDirect(mData: BaseNetModel<CalcDirectBean>)
    }

    interface Presenter {
        fun addrDefault()
        fun orderSubmit(createOrderReq: CreateOrderReq)
        fun calcDirect(createOrderReq: CreateOrderReq)
    }
}