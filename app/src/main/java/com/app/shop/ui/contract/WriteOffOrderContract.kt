package com.app.shop.ui.contract

import com.app.shop.base.BaseContract
import com.app.shop.bean.BaseNetModel
import com.app.shop.bean.VerifyListBean
import com.app.shop.req.OrderIdReq
import retrofit2.http.Body

/**
 * @author chenshichun
 * 创建日期：2022/8/23
 * 描述：
 *
 */
interface WriteOffOrderContract : BaseContract {
    interface View : BaseContract.BaseView {
        fun getVerifyList(mData: BaseNetModel<VerifyListBean>)
        fun orderDelete(mData: BaseNetModel<Any>)
    }

    interface Presenter {
        fun getVerifyList(
            page: Int,
            size: Int
        )

        fun orderDelete(orderIdReq: OrderIdReq)
    }
}