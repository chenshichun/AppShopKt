package com.app.shop.ui.contract

import com.app.shop.base.BaseContract
import com.app.shop.bean.BaseNetModel
import com.app.shop.bean.Prod
import com.app.shop.bean.ShopInfoBean
import com.app.shop.bean.StoreGoodsBean
import com.app.shop.req.StoreIdReq

/**
 * @author chenshichun
 * 创建日期：2022/8/10
 * 描述：
 *
 */
interface StoreHomepageContract : BaseContract {
    interface View : BaseContract.BaseView {
        fun storeAdd(mData: BaseNetModel<Any>)
        fun storeDel(mData: BaseNetModel<Any>)
        fun getStoreDetail(mData: BaseNetModel<ShopInfoBean>)
        fun getStoreGoodsDetail(mData: BaseNetModel<StoreGoodsBean>)
    }

    interface Presenter {
        fun getStoreDetail(storeId: String)
        fun getStoreGoodsDetail(
            page: Int,
            size: Int,
            sort: String,
            keywords:String,
            shopId: String
        )

        fun storeAdd(storeIdReq: StoreIdReq)
        fun storeDel(storeIdReq: StoreIdReq)

    }
}