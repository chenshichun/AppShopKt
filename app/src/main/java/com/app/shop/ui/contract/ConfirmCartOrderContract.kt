package com.app.shop.ui.contract

import com.app.shop.base.BaseContract
import com.app.shop.bean.BaseNetModel
import com.app.shop.bean.CartOrderBean
import com.app.shop.bean.DefaultDaarBean
import com.app.shop.req.CartIdReq

/**
 * @author chenshichun
 * 创建日期：2022/10/10
 * 描述：
 */
class ConfirmCartOrderContract : BaseContract {
    interface View : BaseContract.BaseView {
        fun addrDefault(mData: BaseNetModel<DefaultDaarBean>)
        fun orderSubmitCart(mData: BaseNetModel<CartOrderBean>)
    }

    interface Presenter {
        fun orderSubmitCart(cartIdReq: CartIdReq)
        fun addrDefault()
    }
}