package com.app.shop.ui.contract

import com.app.shop.base.BaseContract
import com.app.shop.bean.BaseNetModel
import com.app.shop.bean.SotreListBean

/**
 * @author chenshichun
 * 创建日期：2022/7/18
 * 描述：
 *
 */
interface AttentionContract : BaseContract {
    interface View : BaseContract.BaseView {
        fun storeList(mData: BaseNetModel<SotreListBean>)
        fun noNetworkView()
        fun showError()
    }

    interface Presenter {
        fun storeList()
    }
}