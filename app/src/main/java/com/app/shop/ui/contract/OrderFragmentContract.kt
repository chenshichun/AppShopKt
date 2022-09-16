package com.app.shop.ui.contract

import com.app.shop.base.BaseContract
import com.app.shop.bean.BaseNetModel
import com.app.shop.bean.OrderListBean

/**
 * @author chenshichun
 * 创建日期：2022/7/18
 * 描述：
 *
 */
interface OrderFragmentContract : BaseContract {
    interface View : BaseContract.BaseView {
        fun orderList(mData: BaseNetModel<OrderListBean>)
    }

    interface Presenter {
        fun orderList(status: Int)
    }
}