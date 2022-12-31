package com.app.shop.ui.contract

import com.app.shop.base.BaseContract
import com.app.shop.bean.BaseNetModel
import com.app.shop.bean.OrderDetailBean
import com.app.shop.req.OrderIdReq
import retrofit2.http.Body

/**
 * @author chenshichun
 * 创建日期：2022/9/16
 * 描述：
 */
interface OrderDetailContract : BaseContract {
    interface View : BaseContract.BaseView {
        fun orderDetail(mData: BaseNetModel<OrderDetailBean>)
        fun orderCancel(mData: BaseNetModel<Any>)
        fun orderConfirm(mData: BaseNetModel<Any>)
        fun orderDelete(mData: BaseNetModel<Any>)
    }

    interface Presenter {
        fun orderDetail(orderId: String)
        fun orderCancel(orderIdReq: OrderIdReq)
        fun orderConfirm(orderIdReq: OrderIdReq)
        fun orderDelete(orderIdReq: OrderIdReq)
    }
}