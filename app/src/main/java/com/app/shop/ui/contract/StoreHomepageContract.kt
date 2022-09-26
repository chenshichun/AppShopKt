package com.app.shop.ui.contract

import com.app.shop.base.BaseContract
import com.app.shop.bean.BaseNetModel
import com.app.shop.bean.ServiceStoreListBean
import com.app.shop.bean.ShopInfoBean
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
        fun getStoreDetail(mData: BaseNetModel<ShopInfoBean>);
    }

    interface Presenter {
        fun getStoreDetail(storeId: String);
        fun storeAdd(storeIdReq: StoreIdReq)
        fun storeDel(storeIdReq: StoreIdReq)
    }
}