package com.app.shop.ui.contract

import com.app.shop.base.BaseContract
import com.app.shop.bean.BaseNetModel
import com.app.shop.bean.OrderListBean
import com.app.shop.req.OrderIdReq

/**
 * @author chenshichun
 * 创建日期：2022/7/18
 * 描述：
 *
 */
interface OrderFragmentContract : BaseContract {
    interface View : BaseContract.BaseView {
        fun orderList(mData: BaseNetModel<OrderListBean>)
        fun orderCancel(mData: BaseNetModel<Any>)
        fun orderConfirm(mData: BaseNetModel<Any>)
        fun orderDelete(mData: BaseNetModel<Any>)
    }

    interface Presenter {
        fun orderList(
            page: Int,
            size: Int, status: Int
        )
        fun orderCancel(orderIdReq: OrderIdReq)
        fun orderConfirm(orderIdReq: OrderIdReq)
        fun orderDelete(orderIdReq: OrderIdReq)
    }
}