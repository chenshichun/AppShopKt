package com.app.shop.ui.contract

import com.app.shop.base.BaseContract
import com.app.shop.bean.BaseNetModel
import com.app.shop.bean.ProdBean

/**
 * @author chenshichun
 * 创建日期：2022/7/28
 * 描述：
 *
 */
interface CategoryListContract : BaseContract {
    interface View : BaseContract.BaseView {
        fun getProdHomeData(mData: BaseNetModel<ProdBean>)
        fun noNetworkView()
        fun showError()
    }

    interface Presenter {
        fun getCateByIdData(page: Int, size: Int, cateId: String, sort: String)
        fun getProdFeaturedData(page: Int, size: Int, sort: String)
        fun getProdRecommendData(page: Int, size: Int, sort: String)
    }
}