package com.app.shop.ui.contract

import com.app.shop.base.BaseContract
import com.app.shop.bean.BaseNetModel
import com.app.shop.bean.ServiceStoreListBean

/**
 * @author chenshichun
 * 创建日期：2022/8/25
 * 描述：
 *
 */
interface OperationsCenterContract : BaseContract {
    interface View : BaseContract.BaseView {
        fun storeServiceList(mData: BaseNetModel<ServiceStoreListBean>)
    }

    interface Presenter {
        fun storeServiceList(
            page: Int,
            size: Int,
            dist: Int,
            search_name: String
        )
    }
}