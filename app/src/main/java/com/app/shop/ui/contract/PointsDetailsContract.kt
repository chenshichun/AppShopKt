package com.app.shop.ui.contract

import com.app.shop.base.BaseContract
import com.app.shop.bean.BaseNetModel
import com.app.shop.bean.PointBean

/**
 * @author chenshichun
 * 创建日期：2022/7/25
 * 描述：
 *
 */
interface PointsDetailsContract : BaseContract {
    interface View : BaseContract.BaseView {
        fun listPoint(mData: BaseNetModel<PointBean>)
        fun noNetworkView()
        fun showError()
    }

    interface Presenter {
        fun listPoint(page:Int,size:Int)
    }
}