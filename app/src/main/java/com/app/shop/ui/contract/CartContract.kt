package com.app.shop.ui.contract

import com.app.shop.base.BaseContract
import com.app.shop.bean.BaseNetModel
import com.app.shop.bean.CartBean
import com.app.shop.bean.ProdBean
import com.app.shop.req.CartAddReq
import com.app.shop.req.CartIdReq
import com.app.shop.req.CartReq
import retrofit2.Response
import retrofit2.http.Body

/**
 * @author chenshichun
 * 创建日期：2022/7/4
 * 描述：
 *
 */
interface CartContract : BaseContract {
    interface View : BaseContract.BaseView {
        fun getCartData(mData: BaseNetModel<CartBean>)
        fun getGoodsData(mData: BaseNetModel<ProdBean>)
        fun cartDel(mData: BaseNetModel<Any>)
        fun noNetworkView()
        fun showError()
        fun cartAdd(mData: BaseNetModel<Any>)
    }

    interface Presenter {
        fun getCartData()
        fun getGoodsData(page: Int, size: Int)
        fun cartDel(cartReq: CartReq)
        fun cartAdd(cartAddReq: CartAddReq)
    }
}