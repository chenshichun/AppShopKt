package com.app.shop.ui.contract

import com.app.shop.base.BaseContract
import com.app.shop.bean.BaseNetModel
import com.app.shop.bean.ClassificationBean

/**
 * @author chenshichun
 * 创建日期：2022/7/4
 * 描述：
 */
interface ClassificationContract : BaseContract {
    interface View : BaseContract.BaseView {
        fun getCateList(mData: BaseNetModel<ClassificationBean>)
        fun noNetworkView()
        fun showError()
    }

    interface Presenter {
        fun getCateList()
    }
}