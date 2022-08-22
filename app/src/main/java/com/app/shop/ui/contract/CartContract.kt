package com.app.shop.ui.contract

import com.app.shop.base.BaseContract
import com.app.shop.bean.BaseNetModel
import com.app.shop.bean.ProdBean

/**
 * @author chenshichun
 * 创建日期：2022/7/4
 * 描述：
 *
 */
interface CartContract  : BaseContract {
    interface View : BaseContract.BaseView {
        fun getGoodsData(mData: BaseNetModel<ProdBean>)
        fun noNetworkView()
        fun showError()
    }

    interface Presenter {
        fun getGoodsData(
            page: Int,
            size: Int,
        )
    }
}