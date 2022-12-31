package com.app.shop.ui.contract

import com.app.shop.base.BaseContract
import com.app.shop.bean.BaseNetModel
import com.app.shop.bean.LocatStoreListBean
import retrofit2.http.Query

/**
 * @author chenshichun
 * 创建日期：2022/7/4
 * 描述：
 *
 */
interface ShopContract : BaseContract {
    interface View : BaseContract.BaseView {
        fun storeLocalList(mData: BaseNetModel<LocatStoreListBean>)
    }

    interface Presenter {
        fun storeLocalList(
            page: Int,
            size: Int,
            dist: Int,
            search_name: String
        )
    }
}